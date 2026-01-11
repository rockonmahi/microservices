resource "aws_lb" "alb" {
  name               = "${var.project_name}-alb"
  load_balancer_type = "application"
  subnets            = var.subnets
  security_groups    = [var.alb_sg_id]

  tags = {
    Name        = "${var.project_name}-alb"
    Environment = var.project_name
  }
}

resource "aws_lb_target_group" "zipkin_alb_target_group" {
  name        = "${var.project_name}-alb-tg-zipkin"
  port        = var.zipkin_port
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  health_check {
    enabled             = true
    path                = "/actuator/health"
    port                = "traffic-port"
    protocol            = "HTTP"
    matcher             = "200"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }

  tags = {
    Name        = "${var.project_name}-alb-tg-zipkin"
    Environment = var.project_name
  }
}

resource "aws_lb_target_group" "web_server_alb_target_group" {
  name        = "${var.project_name}-alb-tg-web-server"
  port        = var.web_server_port
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  health_check {
    enabled             = true
    path                = "/"
    port                = "traffic-port"
    protocol            = "HTTP"
    matcher             = "200"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }

  tags = {
    Name        = "${var.project_name}-alb-tg-web-server"
    Environment = var.project_name
  }
}

resource "aws_lb_target_group" "registry_service_alb_target_group" {
  name        = "${var.project_name}-alb-tg-registry-service"
  port        = var.registry_service_port
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  health_check {
    enabled             = true
    path                = "/registry-service/actuator/health"
    port                = "traffic-port"
    protocol            = "HTTP"
    matcher             = "200"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }

  tags = {
    Name        = "${var.project_name}-alb-tg-registry-service"
    Environment = var.project_name
  }
}

resource "aws_lb_target_group" "config_server_alb_target_group" {
  name        = "${var.project_name}-alb-tg-config-server"
  port        = var.config_server_port
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  health_check {
    enabled             = true
    path                = "/config-server/actuator/health"
    port                = "traffic-port"
    protocol            = "HTTP"
    matcher             = "200"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }

  tags = {
    Name        = "${var.project_name}-alb-tg-config-server"
    Environment = var.project_name
  }
}

resource "aws_lb_target_group" "api_gateway_alb_target_group" {
  name        = "${var.project_name}-alb-tg-api-gateway"
  port        = var.api_gateway_port
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  health_check {
    enabled             = true
    path                = "/api-gateway/health"
    port                = "traffic-port"
    protocol            = "HTTP"
    matcher             = "200"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }

  tags = {
    Name        = "${var.project_name}-alb-tg-api-gateway"
    Environment = var.project_name
  }
}

resource "aws_lb_listener" "zipkin_alb_listener" {
  load_balancer_arn = aws_lb.alb.arn
  port              = var.zipkin_port
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.zipkin_alb_target_group.arn
  }

  tags = {
    Name        = "${var.project_name}-alb-listener-zipkin"
    Environment = var.project_name
  }
}

resource "aws_lb_listener" "web_server_alb_listener" {
  load_balancer_arn = aws_lb.alb.arn
  port              = var.web_server_port
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.web_server_alb_target_group.arn
  }

  tags = {
    Name        = "${var.project_name}-alb-listener-web-server"
    Environment = var.project_name
  }
}

resource "aws_lb_listener_rule" "registry_service_listener_rule" {
  listener_arn = aws_lb_listener.web_server_alb_listener.arn
  priority     = 12

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.registry_service_alb_target_group.arn
  }

  condition {
    path_pattern {
      values = ["/registry-service","/registry-service/*"]
    }
  }
}

resource "aws_lb_listener_rule" "config_server_listener_rule" {
  listener_arn = aws_lb_listener.web_server_alb_listener.arn
  priority     = 13

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.config_server_alb_target_group.arn
  }

  condition {
    path_pattern {
      values = ["/config-server","/config-server/*"]
    }
  }
}

resource "aws_lb_listener_rule" "api_gateway_listener_rule" {
  listener_arn = aws_lb_listener.web_server_alb_listener.arn
  priority     = 14

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.api_gateway_alb_target_group.arn
  }

  condition {
    path_pattern {
      values = ["/api-gateway","/api-gateway/*"]
    }
  }
}