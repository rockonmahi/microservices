module "vpc" {
  source       = "./modules/vpc"
  project_name = var.project_name
}

module "security" {
  source           = "./modules/security"
  project_name     = var.project_name
  vpc_id           = module.vpc.vpc_id
  web_server_port  = module.ecs.web_server_port
  api_gateway_port = module.ecs.api_gateway_port
}

module "alb" {
  source                = "./modules/alb"
  project_name          = var.project_name
  vpc_id                = module.vpc.vpc_id
  subnets               = [module.vpc.public_subnet_1a_id, module.vpc.public_subnet_1b_id]
  alb_sg_id             = module.security.alb_sg_id
  web_server_port       = module.ecs.web_server_port
  registry_service_port = module.ecs.registry_service_port
  config_server_port    = module.ecs.config_server_port
  api_gateway_port      = module.ecs.api_gateway_port
}

module "iam" {
  source       = "./modules/iam"
  project_name = var.project_name
}

module "ecr" {
  source                     = "./modules/ecr"
  project_name               = var.project_name
  web_server_repo_name       = "${var.project_name}-web-server-repo"
  registry_service_repo_name = "${var.project_name}-registry-service-repo"
  config_server_repo_name    = "${var.project_name}-config-server-repo"
  api_gateway_repo_name      = "${var.project_name}-api-gateway-repo"
}

module "ecs" {
  source                                = "./modules/ecs"
  project_name                          = var.project_name
  cluster_name                          = "${var.project_name}-microservice-ecs-cluster"
  web_server_name                       = "web-server"
  web_server_port                       = 80
  registry_service_name                 = "registry-service"
  registry_service_port                 = 5112
  config_server_name                    = "config-server"
  config_server_port                    = 5113
  api_gateway_name                      = "api-gateway"
  api_gateway_port                      = 5114
  public_subnets                        = module.vpc.public_subnet_1a_id
  private_subnets                       = module.vpc.private_subnet_1a_id
  ecs_sg_id                             = module.security.ecs_sg_id
  web_server_alb_target_group_arn       = module.alb.web_server_alb_target_group_arn
  web_server_repository_url             = module.ecr.web_server_ecr_repository_url
  registry_service_alb_target_group_arn = module.alb.registry_service_alb_target_group_arn
  registry_service_repository_url       = module.ecr.registry_service_ecr_repository_url
  config_server_alb_target_group_arn    = module.alb.config_server_alb_target_group_arn
  config_server_repository_url          = module.ecr.config_server_ecr_repository_url
  api_gateway_alb_target_group_arn      = module.alb.api_gateway_alb_target_group_arn
  api_gateway_repository_url            = module.ecr.api_gateway_ecr_repository_url
  ecs_execution_role                    = module.iam.ecs_execution_role
}

/*
module "eks" {
  source                      = "./modules/eks"
  project_name                = var.project_name
  private_subnets_id          = [module.vpc.private_subnet_1a_id, module.vpc.private_subnet_1b_id]
  cluster_name                = "microservice-eks-cluster"
  eks_cluster_role            = module.iam.eks_cluster_role
  eks_node_role               = module.iam.eks_node_role
  eks_worker_node_policy      = module.iam.eks_worker_node_policy
  ec2_container_registry_read = module.iam.ec2_container_registry_read
  eks_cni_policy              = module.iam.eks_cni_policy
}*/
