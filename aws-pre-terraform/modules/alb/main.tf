resource "aws_lb" "alb" {
  name = "${var.project_name}-alb"
  load_balancer_type = "application"
  subnets            = var.subnets
  security_groups    = [var.alb_sg_id]

  tags = {
    Name = "${var.project_name}-alb"
    Environment = var.project_name
  }
}

resource "aws_lb_target_group" "alb_web_server_target_group" {
  name        = "${var.project_name}-alb-tg"
  port        = 80
  protocol    = "HTTP"
  vpc_id      = var.vpc_id
  target_type = "ip"

  tags = {
    Name = "${var.project_name}-web-server-alb-tg"
    Environment = var.project_name
  }
}

resource "aws_lb_listener" "alb_web_server_listener" {
  load_balancer_arn = aws_lb.alb.arn
  port              = 80
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.alb_web_server_target_group.arn
  }

  tags = {
    Name = "${var.project_name}-web-server-alb-listener"
    Environment = var.project_name
  }
}