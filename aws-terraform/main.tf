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
  image_url     = module.ecr.repository_url
}

module "ecr" {
  source = "./modules/ecr"
  project_name = var.project_name
  repo_name = "${var.project_name}-microservice-repo"
}

module "ecs" {
  source        = "./modules/ecs"
  project_name  = var.project_name
  service_name  = "order-service"
  private_subnets = module.vpc.private_subnet_1a_id
  ecs_sg_id     = module.security.ecs_sg_id
  target_group  = module.alb.target_group_arn
  image_url     = module.ecr.repository_url
}