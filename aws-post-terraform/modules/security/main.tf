resource "aws_security_group" "alb_sg" {
  name        = "${var.project_name}-alb-sg"
  description = "${var.project_name} security group for alb"
  vpc_id      = var.vpc_id

  ingress {
    from_port   = var.zipkin_port
    to_port     = var.zipkin_port
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = var.web_server_port
    to_port     = var.web_server_port
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = "${var.project_name}-alb-sg"
    Environment = var.project_name
  }
}

resource "aws_security_group" "ecs_sg" {
  name        = "${var.project_name}-ecs-sg"
  description = "${var.project_name} security group for ecs"
  vpc_id      = var.vpc_id

  ingress {
    from_port       = var.zipkin_port
    to_port         = var.zipkin_port
    protocol        = "tcp"
    security_groups = [aws_security_group.alb_sg.id]
  }
  ingress {
    from_port       = var.web_server_port
    to_port         = var.web_server_port
    protocol        = "tcp"
    security_groups = [aws_security_group.alb_sg.id]
  }
  ingress {
    from_port       = var.registry_service_port
    to_port         = var.registry_service_port
    protocol        = "tcp"
    security_groups = [aws_security_group.alb_sg.id]
  }
  ingress {
    from_port       = var.config_server_port
    to_port         = var.config_server_port
    protocol        = "tcp"
    security_groups = [aws_security_group.alb_sg.id]
  }
  ingress {
    from_port       = var.api_gateway_port
    to_port         = var.api_gateway_port
    protocol        = "tcp"
    security_groups = [aws_security_group.alb_sg.id]
  }
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = "${var.project_name}-ecs-sg"
    Environment = var.project_name
  }
}

resource "aws_security_group" "rds_sg" {
  name        = "${var.project_name}-mysql-rds-sg"
  description = "${var.project_name} security group for rds"
  vpc_id      = var.vpc_id

  ingress {
    description = "MySQL"
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    #security_groups = [aws_security_group.ecs_sg.id]
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = "${var.project_name}-mysql-rds-sg"
    Environment = var.project_name
  }
}
