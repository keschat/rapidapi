package com.rapidapi.pos.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    
    @GetMapping
    public String dashboard(Model model) {
        List<DomainInfo> domains = discoverDomains();
        model.addAttribute("domains", domains);
        model.addAttribute("title", "POS Dashboard");
        return "dashboard/index";
    }
    
    private List<DomainInfo> discoverDomains() {
        List<DomainInfo> domains = new ArrayList<>();
        
        // Discover available domains - link to web controllers
        domains.add(new DomainInfo("product", "Products", "Manage products and inventory", "/domains/product"));
        domains.add(new DomainInfo("user", "Users", "Manage users and permissions", "/domains/user"));
        domains.add(new DomainInfo("customer", "Customers", "Manage customer information", "/domains/customer"));
        domains.add(new DomainInfo("transaction", "Transactions", "View sales and transactions", "/domains/transaction"));
        
        return domains;
    }
    
    public static class DomainInfo {
        private final String id;
        private final String name;
        private final String description;
        private final String apiPath;
        
        public DomainInfo(String id, String name, String description, String apiPath) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.apiPath = apiPath;
        }
        
        public String getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public String getApiPath() { return apiPath; }
    }
}