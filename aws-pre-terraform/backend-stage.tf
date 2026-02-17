terraform {
  backend "s3" {
    bucket  = "stage-microservice-terraform-state"
    key     = "terraform.tfstate"
    region  = "ap-south-1"
    encrypt = true
  }
}
