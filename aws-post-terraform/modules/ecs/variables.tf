variable "project_name" {
}

variable "aws_region" {
}

variable "public_subnets" {
}

variable "private_subnets" {
}

variable "ecs_sg_id" {
}

variable "web_server_alb_target_group_arn" {
}

variable "web_server_repository_url" {
}

variable "registry_service_alb_target_group_arn" {
}

variable "registry_service_repository_url" {
}

variable "config_server_alb_target_group_arn" {
}

variable "config_server_repository_url" {
}

variable "api_gateway_alb_target_group_arn" {
}

variable "api_gateway_repository_url" {
}

variable "web_server_name" {
}

variable "web_server_port" {
}

variable "registry_service_name" {
}


variable "registry_service_port" {
}

variable "config_server_port" {
}

variable "config_server_name" {
}

variable "api_gateway_name" {
  description = "ECS service name"
  type        = string
}

variable "api_gateway_port" {
}

variable "cluster_name" {
}

variable "ecs_execution_role" {
}