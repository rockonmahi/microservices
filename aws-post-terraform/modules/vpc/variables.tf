variable "project_name" {
}

variable "vpc_cidr" {
  default = "10.34.0.0/16"
}

variable "public_subnet_1a_cidr" {
  default = "10.34.0.0/20"
}

variable "public_subnet_1b_cidr" {
  default = "10.34.16.0/20"
}

variable "private_subnet_1a_cidr" {
  default = "10.34.32.0/20"
}

variable "private_subnet_1b_cidr" {
  default = "10.34.48.0/20"
}

variable "availability_zone_1a" {
  default = "ap-south-1a"
}

variable "availability_zone_1b" {
  default = "ap-south-1b"
}
