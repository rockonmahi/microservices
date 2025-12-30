output "cluster_name" {
  value = aws_ecs_cluster.ecs_cluster.name
}

output "cluster_arn" {
  value = aws_ecs_cluster.ecs_cluster.arn
}

output "web_server_port" {
  value = var.web_server_port
}
