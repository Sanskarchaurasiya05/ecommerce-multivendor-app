package com.sanskar.ecommerce02.service;

import com.sanskar.ecommerce02.model.SellerReport;

public interface SellerReportService{

    SellerReport getSellerReport(String sellerId);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
