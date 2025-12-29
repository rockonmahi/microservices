output "alb_url" {
  value = module.alb.alb_dns
}

output "ecr_repo_url" {
  value = module.ecr.repository_url
}

output "ecs_cluster_name" {
  value = module.ecs.cluster_name
}

output "ecs_cluster_arn" {
  value = module.ecs.cluster_arn
}

output "eks_cluster_name" {
  value = module.eks.cluster_name
}

output "eks_cluster_endpoint" {
  value = module.eks.cluster_endpoint
}
