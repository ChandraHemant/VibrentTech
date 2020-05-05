package htkc.ebaba.ecom.vibrentech.api;

import htkc.ebaba.ecom.vibrentech.response.AddCartResponse;
import htkc.ebaba.ecom.vibrentech.response.CartResponse;
import htkc.ebaba.ecom.vibrentech.response.CategoryRetResponse;
import htkc.ebaba.ecom.vibrentech.response.ProductDetailResponse;
import htkc.ebaba.ecom.vibrentech.response.ProductRetResponse;
import htkc.ebaba.ecom.vibrentech.response.RegisterResponse;
import htkc.ebaba.ecom.vibrentech.response.ResendResponse;
import htkc.ebaba.ecom.vibrentech.response.SliderRetResponse;
import htkc.ebaba.ecom.vibrentech.response.SubCategoryRetResponse;
import htkc.ebaba.ecom.vibrentech.response.UpdateCartResponse;
import htkc.ebaba.ecom.vibrentech.response.UserResponse;
import htkc.ebaba.ecom.vibrentech.response.VerifyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService
{


    @FormUrlEncoded
    @POST("verify")
    Call<VerifyResponse> verify(
            @Field("otp") String otp,
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("login")
    Call<UserResponse> login(
            @Field("phone") String phone,
            @Field("pass") String pass
    );

    @GET("allcategory")
    Call<List<CategoryRetResponse>> getCategory();

    @GET("allslider")
    Call<List<SliderRetResponse>> getSlider();


    @FormUrlEncoded
    @POST("cart")
    Call<List<CartResponse>> getCart(
            @Field("u_id") String u_id
    );


    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> register(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("addtocart")
    Call<AddCartResponse> addToCart(
            @Field("uid") String uid,
            @Field("qntty") String qntty,
            @Field("pid") String pid,
            @Field("price") String price
    );
    @FormUrlEncoded
    @POST("updatecart")
    Call<UpdateCartResponse> updateCart(
            @Field("cid") String cid,
            @Field("qntty") String qntty
    );

    @FormUrlEncoded
    @POST("resendotp")
    Call<ResendResponse> resend(
            @Field("mobile") String mobile
    );

    @FormUrlEncoded
    @POST("getsubcategory")
    Call<List<SubCategoryRetResponse>> getSubCategory(
            @Field("cat_id") String cat_id
    );

    @FormUrlEncoded
    @POST("allproducts")
    Call<List<ProductRetResponse>> getAllProduct(
            @Field("sub_id") String sub_id
    );
    @FormUrlEncoded
    @POST("productdetail")
    Call<ProductDetailResponse> getProductDetail(
            @Field("p_id") String p_id
    );
}
