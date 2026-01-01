output "ecr_web_server_repository" {
  value = aws_ecr_repository.ecr_web_server_repository.repository_url
}

output "ecr_config_server_repository" {
  value = aws_ecr_repository.ecr_config_server_repository.repository_url
}

output "ecr_api_gateway_repository" {
  value = aws_ecr_repository.ecr_api_gateway_repository.repository_url
}
