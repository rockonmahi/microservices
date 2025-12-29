output "alb_url" {
  value = module.alb.alb_dns
}

output "ecr_web_server_repository" {
  value = module.ecr.ecr_web_server_repository
}

output "ecr_api_gateway_repository" {
  value = module.ecr.ecr_api_gateway_repository
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
