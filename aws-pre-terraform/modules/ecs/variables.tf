variable "project_name" {
}

variable "public_subnets" {
}

variable "private_subnets" {
}

variable "ecs_sg_id" {
}

variable "web_server_target_group" {
}

variable "web_server_repository_url" {
}

variable "service_name" {
  description = "ECS service name"
  type        = string
}

variable "cluster_name" {
}

variable "ecs_execution_role" {
}