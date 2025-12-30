resource "aws_vpc" "vpc" {
  cidr_block           = var.vpc_cidr
  enable_dns_support   = true
  enable_dns_hostnames = true

  tags = {
    Name        = "${var.project_name}-vpc"
    Environment = var.project_name
  }
}

resource "aws_subnet" "public_subnet_1a" {
  vpc_id                  = aws_vpc.vpc.id
  cidr_block              = var.public_subnet_1a_cidr
  availability_zone       = var.availability_zone_1a
  map_public_ip_on_launch = true

  tags = {
    Name        = "${var.project_name}-public-subnet-1a"
    Environment = var.project_name
  }
}

resource "aws_subnet" "public_subnet_1b" {
  vpc_id                  = aws_vpc.vpc.id
  cidr_block              = var.public_subnet_1b_cidr
  availability_zone       = var.availability_zone_1b
  map_public_ip_on_launch = true

  tags = {
    Name        = "${var.project_name}-public-subnet-1b"
    Environment = var.project_name
  }
}

resource "aws_subnet" "private_subnet_1a" {
  vpc_id            = aws_vpc.vpc.id
  cidr_block        = var.private_subnet_1a_cidr
  availability_zone = var.availability_zone_1a

  tags = {
    Name        = "${var.project_name}-private-subnet-1a"
    Environment = var.project_name
  }
}

resource "aws_subnet" "private_subnet_1b" {
  vpc_id            = aws_vpc.vpc.id
  cidr_block        = var.private_subnet_1b_cidr
  availability_zone = var.availability_zone_1b

  tags = {
    Name        = "${var.project_name}-private-subnet-1b"
    Environment = var.project_name
  }
}

resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.vpc.id

  tags = {
    Name        = "${var.project_name}-igw"
    Environment = var.project_name
  }
}

resource "aws_eip" "nat_eip" {
  domain = "vpc"

  tags = {
    Name        = "${var.project_name}-nat-eip"
    Environment = var.project_name
  }
}

resource "aws_nat_gateway" "nat" {
  allocation_id = aws_eip.nat_eip.id
  subnet_id     = aws_subnet.public_subnet_1a.id

  tags = {
    Name        = "${var.project_name}-nat-gateway"
    Environment = var.project_name
  }

  depends_on = [aws_internet_gateway.igw]
}

resource "aws_route_table" "public_rt" {
  vpc_id = aws_vpc.vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw.id
  }

  tags = {
    Name        = "${var.project_name}-public-rt"
    Environment = var.project_name
  }
}

resource "aws_route_table_association" "public_association_1a" {
  subnet_id      = aws_subnet.public_subnet_1a.id
  route_table_id = aws_route_table.public_rt.id
}

resource "aws_route_table_association" "public_association_1b" {
  subnet_id      = aws_subnet.public_subnet_1b.id
  route_table_id = aws_route_table.public_rt.id
}

resource "aws_route_table_association" "private_association_1a" {
  subnet_id      = aws_subnet.private_subnet_1a.id
  route_table_id = aws_route_table.private_rt.id
}

resource "aws_route_table_association" "private_association_1b" {
  subnet_id      = aws_subnet.private_subnet_1b.id
  route_table_id = aws_route_table.private_rt.id
}

resource "aws_route_table" "private_rt" {
  vpc_id = aws_vpc.vpc.id

  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.nat.id
  }

  tags = {
    Name        = "${var.project_name}-private-rt"
    Environment = var.project_name
  }
}
