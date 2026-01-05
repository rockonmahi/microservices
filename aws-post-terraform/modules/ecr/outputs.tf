output "zipkin_ecr_repository_url" {
  value = aws_ecr_repository.zipkin_ecr_repository.repository_url
}

output "web_server_ecr_repository_url" {
  value = aws_ecr_repository.web_server_ecr_repository.repository_url
}

output "registry_service_ecr_repository_url" {
  value = aws_ecr_repository.registry_service_ecr_repository.repository_url
}

output "config_server_ecr_repository_url" {
  value = aws_ecr_repository.config_server_ecr_repository.repository_url
}

output "api_gateway_ecr_repository_url" {
  value = aws_ecr_repository.api_gateway_ecr_repository.repository_url
}
