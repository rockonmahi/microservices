resource "aws_ecr_repository" "zipkin_ecr_repository" {
  name = var.zipkin_repo_name

  tags = {
    Name        = "${var.project_name}-ecr-zipkin"
    Environment = var.project_name
  }
}

resource "aws_ecr_repository" "web_server_ecr_repository" {
  name = var.web_server_repo_name

  tags = {
    Name        = "${var.project_name}-ecr-web-server"
    Environment = var.project_name
  }
}

resource "aws_ecr_repository" "registry_service_ecr_repository" {
  name = var.registry_service_repo_name

  tags = {
    Name        = "${var.project_name}-ecr-registry-service"
    Environment = var.project_name
  }
}

resource "aws_ecr_repository" "config_server_ecr_repository" {
  name = var.config_server_repo_name

  tags = {
    Name        = "${var.project_name}-ecr-config-server"
    Environment = var.project_name
  }
}

resource "aws_ecr_repository" "api_gateway_ecr_repository" {
  name = var.api_gateway_repo_name

  tags = {
    Name        = "${var.project_name}-ecr-api-gateway"
    Environment = var.project_name
  }
}
