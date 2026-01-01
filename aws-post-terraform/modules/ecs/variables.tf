variable "project_name" {
}

variable "public_subnets" {
}

variable "private_subnets" {
}

variable "ecs_sg_id" {
}

variable "alb_web_server_target_group_arn" {
}

variable "web_server_repository_url" {
}

variable "alb_config_server_target_group_arn" {
}

variable "config_server_repository_url" {
}

variable "alb_api_gateway_target_group_arn" {
}

variable "api_gateway_repository_url" {
}

variable "web_server_name" {
  description = "ECS service name"
  type        = string
}

variable "web_server_port" {
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