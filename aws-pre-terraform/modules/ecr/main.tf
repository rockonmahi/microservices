resource "aws_ecr_repository" "ecr_web_server_repository" {
  name = var.web_server_repo_name

  tags = {
    Name = "${var.project_name}-ecr-web-server"
    Environment = var.project_name
  }
}

resource "aws_ecr_repository" "ecr_api_gateway_repository" {
  name = var.api_gateway_repo_name

  tags = {
    Name = "${var.project_name}-ecr-api-gateway"
    Environment = var.project_name
  }
}