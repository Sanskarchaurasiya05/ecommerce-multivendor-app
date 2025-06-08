package com.sanskar.ecommerce02.service;

import com.sanskar.ecommerce02.model.Seller;
import com.sanskar.ecommerce02.model.SellerReport;

public interface SellerReportService{

    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
