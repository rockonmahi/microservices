module "vpc" {
  source = "./modules/vpc"
  project_name = var.project_name
}

module "security" {
  source = "./modules/security"
  project_name = var.project_name
  vpc_id = module.vpc.vpc_id
}

module "alb" {
  source     = "./modules/alb"
  project_name = var.project_name
  vpc_id     = module.vpc.vpc_id
  subnets    = [module.vpc.public_subnet_1a_id,module.vpc.public_subnet_1b_id]
  alb_sg_id  = module.security.alb_sg_id
}

module "iam" {
  source        = "./modules/iam"
  project_name  = var.project_name
}

module "ecr" {
  source = "./modules/ecr"
  project_name = var.project_name
  web_server_repo_name = "${var.project_name}-web-server-repo"
  api_gateway_repo_name = "${var.project_name}-api-gateway-repo"
}

/*module "ecs" {
  source        = "./modules/ecs"
  project_name  = var.project_name
  cluster_name = "microservice-ecs-cluster"
  service_name  = "web-server"
  private_subnets = module.vpc.private_subnet_1a_id
  ecs_sg_id     = module.security.ecs_sg_id
  target_group  = module.alb.target_group_arn
  web_server_repository_url     = module.ecr.ecr_web_server_repository
  ecs_execution_role = module.iam.ecs_execution_role
}
*/
/*module "eks" {
  source        = "./modules/eks"
  project_name  = var.project_name
  private_subnets_id = [module.vpc.private_subnet_1a_id,module.vpc.private_subnet_1b_id]
  cluster_name = "microservice-eks-cluster"
  eks_cluster_role = module.iam.eks_cluster_role
  eks_node_role = module.iam.eks_node_role
  eks_worker_node_policy = module.iam.eks_worker_node_policy
  ecr_read = module.iam.ecr_read
  eks_cni_policy = module.iam.eks_cni_policy
}*/