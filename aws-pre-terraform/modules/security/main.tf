resource "aws_security_group" "alb" {
  name   = "${var.project_name}-alb-sg"
  description = "${var.project_name} security group for alb"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${var.project_name}-alb-sg"
    Environment = var.project_name
  }
}

resource "aws_security_group" "ecs" {
  name   = "${var.project_name}-ecs-sg"
  description = "${var.project_name} security group for ecs"
  vpc_id = var.vpc_id

  ingress {
    from_port       = 80
    to_port         = 80
    protocol        = "tcp"
    security_groups = [aws_security_group.alb.id]
  }

  tags = {
    Name = "${var.project_name}-ecs-sg"
    Environment = var.project_name
  }
}