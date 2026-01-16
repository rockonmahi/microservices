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

output "api_gateway_ecr_repository_url" {
  value = module.ecr.api_gateway_ecr_repository_url
}
/*
output "rds_endpoint" {
  value = module.rds.rds_endpoint
}

output "eks_cluster_name" {
  value = module.eks.cluster_name
}

output "eks_cluster_endpoint" {
  value = module.eks.cluster_endpoint
}*/
