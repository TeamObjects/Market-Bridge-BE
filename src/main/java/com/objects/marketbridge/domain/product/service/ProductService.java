package com.objects.marketbridge.domain.product.service;

import com.objects.marketbridge.domain.model.*;
import com.objects.marketbridge.domain.product.dto.*;

import com.objects.marketbridge.domain.Image.ImageRepository;
import com.objects.marketbridge.domain.Image.ProductImageRepository;
import com.objects.marketbridge.domain.product.repository.ProductRepository;
import com.objects.marketbridge.domain.category.CategoryRepository;
import com.objects.marketbridge.domain.option.OptionRepository;
import com.objects.marketbridge.domain.option.ProdOptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ProductImageRepository productImageRepository;
    private final OptionRepository optionRepository;
    private final ProdOptionRepository prodOptionRepository;


    //상품등록
    @Transactional
    public Long createProduct(CreateProductRequestDto createProductRequestDto) {

        // category가 DB에 등록되어있다고 가정.
        Category category = categoryRepository.findById(createProductRequestDto.getCategoryId());

        // ProductRequestDto에서 필요한 정보 추출하여 Product 엔터티 생성
        Product product = Product.builder()
                .category(category)
                .isOwn(createProductRequestDto.getIsOwn())
                .name(createProductRequestDto.getName())
                .price(createProductRequestDto.getPrice())
                .isSubs(createProductRequestDto.getIsSubs())
                .stock(createProductRequestDto.getStock())
                .thumbImg(createProductRequestDto.getThumbImg())
                .discountRate(createProductRequestDto.getDiscountRate())
                .build();

        // ProductRepository 통해 엔터티를 저장
        productRepository.save(product);

        // 관련 image, productImage 저장
        saveImageAndProductImage(createProductRequestDto, product);


        // product ----- product_option ---- options ---- option_category 연결되어 있음.
        // 1. option_category테이블에 데이터가 등록되어있다고 가정. (색상, 사이즈 등)
        // 2. options테이블에도 데이터가 등록되어 있다고 가정. (색상-white, 사이즈-XL 등. 따로 api만들어야할듯.)
        // 하나의 상품에대한 재고는(stock)은 options테이블의 해당옵션의 stock(재고)수임.
        // 3. 등록 => ProductRequestDto에 optionNames(String배열)를 Json형식으로 받아와서
        // product(id)와 option(id)가 등록되게 prod_option테이블에 등록.
        List<String> optionNames = createProductRequestDto.getOptionNames();
        for (String optionName : optionNames) {
            ProdOption prodOption = ProdOption.builder()
                    .product(productRepository.findById(product.getId()))
                    .option(optionRepository.findByName(optionName))
                    .build();

            prodOptionRepository.save(prodOption);
        }

        Long productId = product.getId();
        return productId;

    } //상품등록 끝




//    상품조회
    @Transactional
    public ReadProductResponseDto readProduct(Long id){
        Product findProduct = productRepository.findById(id);
        ReadProductResponseDto readProductResponseDto = new ReadProductResponseDto(
                findProduct.getId(),
                findProduct.getCategory().getId(),
                findProduct.getIsOwn(),
                findProduct.getName(),
                findProduct.getPrice(),
                findProduct.getIsSubs(),
                findProduct.getStock(),
                findProduct.getThumbImg(),
                findProduct.getDiscountRate()
        );
        return readProductResponseDto;
    } // 상품조회 끝




//    //상품수정
//    @Transactional
//    public void updateProduct(Long id, UpdateProductRequestDto updateProductRequestDto) {
//
//        // 상품 ID에 해당하는 상품을 찾음
//        Product findProduct = productRepository.findById(id);
//        Category category = categoryRepository.findById(updateProductRequestDto.getCategoryId());
//
//        if(findProduct == null){
//            throw new EntityNotFoundException("[상품수정] 해당 id에 해당하는 상품이 없습니다.");
//        }
//
//        // 업데이트 요청의 내용을 상품에 저장
//        // 끝에 productRepository.save(findProduct);를 안해도 변경감지에 의해 update되는듯함.
//        findProduct.updateProduct(
//                category,
//                updateProductRequestDto.getIsOwn(),
//                updateProductRequestDto.getName(),
//                updateProductRequestDto.getPrice(),
//                updateProductRequestDto.getIsSubs(),
//                updateProductRequestDto.getStock(),
//                updateProductRequestDto.getThumbImg(),
//                updateProductRequestDto.getDiscountRate()
//        );
//
//
//        // 업데이트 요청의 내용 중 itemImg,detailImg에 관한 내용을 image와 productImage에서 변경해야하는데
//        // 전부 삭제후 재등록하는 로직으로 작성함.
//
//        // 관련 image및 productImage삭제로직
//        List<ProductImage> findProductImages = productImageRepository.findAllByProductId(id);
//
//        for (ProductImage findProductImage : findProductImages) {
//            productImageRepository.delete(findProductImage);
//        }
//
//        List<Long> findImageIds
//                = findProductImages.stream()
//                .map(productImage -> productImage.getImage().getId())
//                .collect(Collectors.toList());
//
//        for (Long findImageId : findImageIds) {
//            imageRepository.deleteById(findImageId);
//        }
//
//        // 관련 image 및 productImage 등록
//        updateImageAndProductImage(updateProductRequestDto, findProduct);
//
//
//        // product ----- product_option ---- options ---- option_category 관련은 수정하지 않음.
//
//    } //상품수정 끝




//    @Transactional
//    // 상품삭제
//    public void deleteProduct(Long id){
////        System.out.println("=====" + id + "=====");
//        Product findProduct = productRepository.findById(id);
////        System.out.println("=====" + findProduct +"=====");
//        deleteImageAndProductImage(id);
//        productRepository.delete(findProduct);
//    } //상품삭제 끝





    //ProductService의 내부이용할 메서드들

    //상품등록시의 image와 productimage 저장메서드
    void saveImageAndProductImage(CreateProductRequestDto createProductRequestDto, Product product){
        // 상품등록시 image테이블에 아이템이미지url들 추가, product_image테이블에 추가.
        List<String> itemImgUrls = createProductRequestDto.getItemImgUrls();
        for (String itemImgUrl : itemImgUrls) {
            Image itemImg = Image.builder()
                    .type(ImageType.ITEM_IMG.toString())
                    .url(itemImgUrl).build();
            imageRepository.save(itemImg);

            ProductImage productImage = ProductImage.builder()
                    .image(imageRepository.findById(itemImg.getId()))
                    .product(productRepository.findById(product.getId()))
                    .build();

            productImageRepository.save(productImage);
        }

        // 상품등록시 image테이블에 디테일이미지url들 추가, product_image테이블에 추가.
        List<String> detailImgUrls = createProductRequestDto.getDetailImgUrls();
        for (String detailImgUrl : detailImgUrls) {
            Image detailImg = Image.builder()
                    .type(ImageType.DETAIL_IMG.toString())
                    .url(detailImgUrl).build();
            imageRepository.save(detailImg);

            ProductImage productImage = ProductImage.builder()
                    .image(imageRepository.findById(detailImg.getId()))
                    .product(productRepository.findById(product.getId()))
                    .build();

            productImageRepository.save(productImage);
        }
    }


//    //상품수정시의 image와 productimage 업데이트 메서드
//    void updateImageAndProductImage(UpdateProductRequestDto updateProductRequestDto, Product product){
//        // 관련 image및 productImage 삭제 로직
//        List<ProductImage> findProductImages = productImageRepository.findAllByProductId(product.getId());
//
//        for (ProductImage findProductImage : findProductImages) {
//            productImageRepository.delete(findProductImage);
//        }
//
//        List<Long> findImageIds
//                = findProductImages.stream()
//                .map(productImage -> productImage.getImage().getId())
//                .collect(Collectors.toList());
//
//        for (Long findImageId : findImageIds) {
//            imageRepository.deleteById(findImageId);
//        }
//
//        // 상품등록시 image테이블에 아이템이미지url들 추가, product_image테이블에 추가.
//        List<String> itemImgUrls = updateProductRequestDto.getItemImgUrls();
//        for (String itemImgUrl : itemImgUrls) {
//            Image itemImg = Image.builder()
//                    .type(ImageType.ITEM_IMG.toString())
//                    .url(itemImgUrl).build();
//            imageRepository.save(itemImg);
//
//            ProductImage productImage = ProductImage.builder()
//                    .image(imageRepository.findById(itemImg.getId()))
//                    .product(productRepository.findById(product.getId()))
//                    .build();
//
//            productImageRepository.save(productImage);
//        }
//        // image테이블에 디테일이미지url들 추가, product_image테이블에 추가.
//        List<String> detailImgUrls = updateProductRequestDto.getDetailImgUrls();
//        for (String detailImgUrl : detailImgUrls) {
//            Image detailImg = Image.builder()
//                    .type(ImageType.DETAIL_IMG.toString())
//                    .url(detailImgUrl).build();
//            imageRepository.save(detailImg);
//
//            ProductImage productImage = ProductImage.builder()
//                    .image(imageRepository.findById(detailImg.getId()))
//                    .product(productRepository.findById(product.getId()))
//                    .build();
//
//            productImageRepository.save(productImage);
//        }
//    }


//    //상품삭제시의 image와 productImage 삭제 메서드
//    void deleteImageAndProductImage(Long id) {
//
//        // 관련 image및 productImage삭제로직
//        List<ProductImage> findProductImages = productImageRepository.findAllByProductId(id);
//
//        for (ProductImage findProductImage : findProductImages) {
//            productImageRepository.delete(findProductImage);
//        }
//
//        List<Long> findImageIds
//                = findProductImages.stream()
//                .map(productImage -> productImage.getImage().getId())
//                .collect(Collectors.toList());
//
//        for (Long findImageId : findImageIds) {
//            imageRepository.deleteById(findImageId);
//        }
//    }

}
