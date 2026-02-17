terraform {
  backend "s3" {
    #bucket  = "microservice-terraform-state"
    key     = "terraform.tfstate"
    region  = "ap-south-1"
    encrypt = true
  }
}
