package com.sanskar.ecommerce02.service.impl;

import com.sanskar.ecommerce02.config.JwtProvider;
import com.sanskar.ecommerce02.domain.AccountStatus;
import com.sanskar.ecommerce02.exception.SellerException;
import com.sanskar.ecommerce02.model.Address;
import com.sanskar.ecommerce02.model.Seller;
import com.sanskar.ecommerce02.repository.AddressRepository;
import com.sanskar.ecommerce02.repository.SellerRepository;
import com.sanskar.ecommerce02.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

//    -----------------------------------------------------------------------------------------------------------------
    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        System.out.println(jwt);
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.getSellerByEmail(email);
    }

//    -----------------------------------------------------------------------------------------------------------------
    @Override
    public Seller createSeller(Seller seller) throws Exception {

        Seller sellerExist=sellerRepository.findByEmail(seller.getEmail());
        if(sellerExist!=null)
            throw new Exception("seller already exist,use different email id");
//        it require a pickup address from where the deliveryman can picked the parcel

        Address saveaddress = addressRepository.save(seller.getPickupAddress());

        Seller newseller = new Seller();

        newseller.setEmail(seller.getEmail());
        newseller.setPassword(seller.getPassword());
        newseller.setSellerName(seller.getSellerName());
        newseller.setPickupAddress(saveaddress);
        newseller.setGSTIN(seller.getGSTIN());
        newseller.setRole(seller.getRole());
        newseller.setMobile(seller.getMobile());
        newseller.setBankDetails(seller.getBankDetails());
        newseller.setBusinessDetails(seller.getBusinessDetails());


        return sellerRepository.save(newseller);
    }

//    ---------------------------------------------------------------------------------------------------------------
    @Override
    public Seller getSellerById(Long id) throws SellerException {
        return sellerRepository.findById(id).orElseThrow(()->new SellerException("seller not found with id "+ id ));
    }

//    ------------------------------------------------------------------------------------------------------------
    @Override
    public Seller getSellerByEmail(String email) throws Exception {

        Seller seller = sellerRepository.findByEmail(email);
        if (seller != null) {
            return seller;
        }
        throw new SellerException("Seller not found");
    }

//    -----------------------------------------------------------------------------------------------------------
    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepository.findByAccountStatus(status);
    }

//    --------------------------------------------------------------------------------------------------------------
    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {

//        for updating the seller first we check it exiting or not
        Seller existingSeller=this.getSellerById(id);

        if(seller.getSellerName()!=null)
            existingSeller.setSellerName(seller.getSellerName());

        if(seller.getMobile()!=null){
            existingSeller.setMobile(seller.getMobile());
        }

        if(seller.getEmail()!=null){
            existingSeller.setEmail(seller.getEmail());
        }
        if(seller.getBankDetails()!=null
        && seller.getBankDetails().getAccountNumber()!=null
        && seller.getBankDetails().getAccountHolderName()!=null
        && seller.getBankDetails().getIfscCode()!=null) {

            existingSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());
            existingSeller.getBankDetails().setAccountNumber(seller.getBankDetails().getAccountNumber());
            existingSeller.getBankDetails().setIfscCode(seller.getBankDetails().getIfscCode());
        }



        if(seller.getBusinessDetails()!=null
        && seller.getBusinessDetails().getBusinessName()!=null){
            existingSeller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());
        }

        if (seller.getPickupAddress() != null
                && seller.getPickupAddress().getAddress() != null
                && seller.getPickupAddress().getMobile() != null
                && seller.getPickupAddress().getCity() != null
                && seller.getPickupAddress().getState() != null
        ) {
            existingSeller.getPickupAddress()
                    .setAddress(seller.getPickupAddress().getAddress());
            existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
            existingSeller.getPickupAddress().setState(seller.getPickupAddress().getState());
            existingSeller.getPickupAddress().setMobile(seller.getPickupAddress().getMobile());
            existingSeller.getPickupAddress().setPinCode(seller.getPickupAddress().getPinCode());
        }
        if (seller.getGSTIN() != null) {
            existingSeller.setGSTIN(seller.getGSTIN());
        }



        return sellerRepository.save(existingSeller);
    }

//    ----------------------------------------------------------------------------------------------------------

    @Override
    public void deleteSeller(Long id) throws Exception {
      Seller seller = getSellerById(id);
      sellerRepository.delete(seller);
    }

//    ---------------------------------------------------------------------------------------------------------------

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
        Seller seller = getSellerByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

//    ----------------------------------------------------------------------------------------------------

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {
        Seller seller = getSellerById(sellerId);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }
}
