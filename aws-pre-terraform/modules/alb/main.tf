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

resource "aws_lb_target_group" "web_server_alb_target_group" {
  name        = "${var.project_name}-alb-tg-web-server"
  port        = var.web_server_port
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  tags = {
    Name        = "${var.project_name}-alb-tg-web-server"
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

resource "aws_lb_target_group" "registry_service_alb_target_group" {
  name        = "${var.project_name}-alb-tg-registry-service"
  port        = var.registry_service_port
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  tags = {
    Name        = "${var.project_name}-alb-tg-registry-service"
    Environment = var.project_name
  }
}

resource "aws_lb_listener" "registry_service_alb_listener" {
  load_balancer_arn = aws_lb.alb.arn
  port              = var.registry_service_port
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.registry_service_alb_target_group.arn
  }

  tags = {
    Name        = "${var.project_name}-alb-listener-registry-service"
    Environment = var.project_name
  }
}

resource "aws_lb_target_group" "config_server_alb_target_group" {
  name        = "${var.project_name}-alb-tg-config-server"
  port        = var.config_server_port
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  tags = {
    Name        = "${var.project_name}-alb-tg-config-server"
    Environment = var.project_name
  }
}

resource "aws_lb_listener" "config_server_alb_listener" {
  load_balancer_arn = aws_lb.alb.arn
  port              = var.config_server_port
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.config_server_alb_target_group.arn
  }

  tags = {
    Name        = "${var.project_name}-alb-listener-config-server"
    Environment = var.project_name
  }
}

resource "aws_lb_target_group" "api_gateway_alb_target_group" {
  name        = "${var.project_name}-alb-tg-api-gateway"
  port        = var.api_gateway_port
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  tags = {
    Name        = "${var.project_name}-alb-tg-api-gateway"
    Environment = var.project_name
  }
}

resource "aws_lb_listener" "api_gateway_alb_listener" {
  load_balancer_arn = aws_lb.alb.arn
  port              = var.api_gateway_port
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.api_gateway_alb_target_group.arn
  }

  tags = {
    Name        = "${var.project_name}-alb-listener-api-gateway"
    Environment = var.project_name
  }
}