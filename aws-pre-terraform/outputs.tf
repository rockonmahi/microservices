output "alb_dns_url" {
  value = module.alb.alb_dns
}

output "zipkin_ecr_repository_url" {
  value = module.ecr.zipkin_ecr_repository_url
}

output "web_server_ecr_repository_url" {
  value = module.ecr.web_server_ecr_repository_url
}

output "registry_service_ecr_repository_url" {
  value = module.ecr.registry_service_ecr_repository_url
}

output "config_server_ecr_repository_url" {
  value = module.ecr.config_server_ecr_repository_url
}

output "ecr_api_gateway_repository" {
  value = module.ecr.api_gateway_ecr_repository_url
}

output "ecs_cluster_name" {
  value = module.ecs.cluster_name
}

output "ecs_cluster_arn" {
  value = module.ecs.cluster_arn
}
/*
output "eks_cluster_name" {
  value = module.eks.cluster_name
}

output "eks_cluster_endpoint" {
  value = module.eks.cluster_endpoint
}*/
