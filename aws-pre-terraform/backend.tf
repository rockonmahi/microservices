terraform {
  backend "s3" {
    bucket  = "dev-microservice-terraform-state"
    key     = "eks/terraform.tfstate"
    region  = "ap-south-1"
    encrypt = true
  }
}
