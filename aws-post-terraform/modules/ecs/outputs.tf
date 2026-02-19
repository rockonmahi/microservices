output "cluster_name" {
  value = aws_ecs_cluster.ecs_cluster.name
}

output "cluster_arn" {
  value = aws_ecs_cluster.ecs_cluster.arn
}

output "zipkin_port" {
  value = var.zipkin_port
}

output "web_server_port" {
  value = var.web_server_port
}

output "registry_service_port" {
  value = var.registry_service_port
}

output "config_server_port" {
  value = var.config_server_port
}

output "api_gateway_port" {
  value = var.api_gateway_port
}
