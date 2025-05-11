# Three-Tier-Web-Based-Application

## Overview

This project is a distributed three-tier web-based application developed using Java Servlets, JSP, Apache Tomcat, and MySQL. It demonstrates user role-based access, authentication, stored procedures, and business logic integration via a multi-tier architecture.

Users authenticate through a login system and are routed to different interfaces depending on their role: `root`, `client`, or `accountant`.

---

## Architecture

**Tier 1 – Front-End**
- HTML landing page for login
- JSP-based user interfaces for all roles
- Role-based functionality per user access level

**Tier 2 – Middle Layer (Business/Application Logic)**
- Servlets handling:
  - Authentication
  - SQL command processing
  - CallableStatement for accountant reports
  - Business rule enforcement for shipment updates

**Tier 3 – Back-End**
- MySQL databases:
  - `project4` (main app DB)
  - `credentialsDB` (stores user credentials)

---

