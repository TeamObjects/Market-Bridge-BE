INSERT INTO member_coupon (member_id, coupon_id, is_used, used_date, end_date, created_at, updated_at, deleted_at)
VALUES
    (1, 1, 0, NULL, NOW(), NOW(), NOW(), NULL),
    (2, 2, 0, NULL, NOW(), NOW(), NOW(), NULL),
    (3, 3, 0, NULL, NOW(), NOW(), NOW(), NULL),
    (4, 4, 0, NULL, NOW(), NOW(), NOW(), NULL),
    (5, 5, 0, NULL, NOW(), NOW(), NOW(), NULL),
    (6, 6, 0, NULL, NOW(), NOW(), NOW(), NULL),
    (7, 7, 0, NULL, NOW(), NOW(), NOW(), NULL),
    (8, 8, 0, NULL, NOW(), NOW(), NOW(), NULL),
    (9, 9, 0, NULL, NOW(), NOW(), NOW(), NULL),
    (10, 10, 0, NULL, NOW(), NOW(), NOW(), NULL);

INSERT INTO coupon (product_id, name, price, count, minimum_price, start_date, end_date, created_at, updated_at, deleted_at)
VALUES
    (1, 'CouponName1', 'CouponPrice1', 1, 100, NOW(), NOW(), NOW(), NOW(), NULL),
    (2, 'CouponName2', 'CouponPrice2', 2, 200, NOW(), NOW(), NOW(), NOW(), NULL),
    (3, 'CouponName3', 'CouponPrice3', 3, 300, NOW(), NOW(), NOW(), NOW(), NULL),
    (4, 'CouponName4', 'CouponPrice4', 4, 400, NOW(), NOW(), NOW(), NOW(), NULL),
    (5, 'CouponName5', 'CouponPrice5', 5, 500, NOW(), NOW(), NOW(), NOW(), NULL),
    (6, 'CouponName6', 'CouponPrice6', 6, 600, NOW(), NOW(), NOW(), NOW(), NULL),
    (7, 'CouponName7', 'CouponPrice7', 7, 700, NOW(), NOW(), NOW(), NOW(), NULL),
    (8, 'CouponName8', 'CouponPrice8', 8, 800, NOW(), NOW(), NOW(), NOW(), NULL),
    (9, 'CouponName9', 'CouponPrice9', 9, 900, NOW(), NOW(), NOW(), NOW(), NULL),
    (10, 'CouponName10', 'CouponPrice10', 10, 1000, NOW(), NOW(), NOW(), NOW(), NULL);

INSERT INTO review_image (review_id, image_id, created_at, updated_at, deleted_at)
VALUES
    (1, 1, NOW(), NOW(), NULL),
    (2, 2, NOW(), NOW(), NULL),
    (3, 3, NOW(), NOW(), NULL),
    (4, 4, NOW(), NOW(), NULL),
    (5, 5, NOW(), NOW(), NULL),
    (6, 6, NOW(), NOW(), NULL),
    (7, 7, NOW(), NOW(), NULL),
    (8, 8, NOW(), NOW(), NULL),
    (9, 9, NOW(), NOW(), NULL),
    (10, 10, NOW(), NOW(), NULL);

INSERT INTO review (member_id, product_id, content, rating, created_at, updated_at, deleted_at)
VALUES
    (1, 1, 'ReviewContent1', 5, NOW(), NOW(), NULL),
    (2, 2, 'ReviewContent2', 4, NOW(), NOW(), NULL),
    (3, 3, 'ReviewContent3', 3, NOW(), NOW(), NULL),
    (4, 4, 'ReviewContent4', 2, NOW(), NOW(), NULL),
    (5, 5, 'ReviewContent5', 1, NOW(), NOW(), NULL),
    (6, 6, 'ReviewContent6', 5, NOW(), NOW(), NULL),
    (7, 7, 'ReviewContent7', 4, NOW(), NOW(), NULL),
    (8, 8, 'ReviewContent8', 3, NOW(), NOW(), NULL),
    (9, 9, 'ReviewContent9', 2, NOW(), NOW(), NULL),
    (10, 10, 'ReviewContent10', 1, NOW(), NOW(), NULL);

INSERT INTO payment (order_id, order_no, payment_method, tid, card_issuer_name, card_purchase_name, card_no, card_install_month, total_amount, discount_amount, tax_free_amount, approved_at, deleted_at, updated_at, created_at)
VALUES
    (1, 'OrderNo1', 'PaymentMethod1', 'TID1', 'CardIssuerName1', 'CardPurchaseName1', 'CardNo1', 1, 100, 10, 5, NOW(), null, NOW(), NOW()),
    (2, 'OrderNo2', 'PaymentMethod2', 'TID2', 'CardIssuerName2', 'CardPurchaseName2', 'CardNo2', 2, 200, 20, 10, NOW(), null, NOW(), NOW()),
    (3, 'OrderNo3', 'PaymentMethod3', 'TID3', 'CardIssuerName3', 'CardPurchaseName3', 'CardNo3', 3, 300, 30, 15, NOW(), null, NOW(), NOW()),
    (4, 'OrderNo4', 'PaymentMethod4', 'TID4', 'CardIssuerName4', 'CardPurchaseName4', 'CardNo4', 4, 400, 40, 20, NOW(), null, NOW(), NOW()),
    (5, 'OrderNo5', 'PaymentMethod5', 'TID5', 'CardIssuerName5', 'CardPurchaseName5', 'CardNo5', 5, 500, 50, 25, NOW(), null, NOW(), NOW()),
    (6, 'OrderNo6', 'PaymentMethod6', 'TID6', 'CardIssuerName6', 'CardPurchaseName6', 'CardNo6', 6, 600, 60, 30, NOW(), null, NOW(), NOW());

INSERT INTO order_detail (order_id, product_id, order_no, member_coupon_id,  quantity, price,  status_code, tid, delivered_date, cancelled_at, created_at, updated_at, deleted_at)
VALUES
    (1, 1, 'OrderNo1', 1, 1, 100,  'StatusCode1', 'TID1', NOW(), NOW(), NOW(), NOW(), NULL),
    (1, 2, 'OrderNo1', 2,  2, 200,  'StatusCode2', 'TID2', NOW(), NOW(), NOW(), NOW(), NULL),
    (1, 3, 'OrderNo1', 3,  3, 300, 'StatusCode3', 'TID3', NOW(), NOW(), NOW(), NOW(), NULL),
    (2, 4, 'OrderNo2', 4,  4, 400,  'StatusCode4', 'TID4', NOW(), NOW(), NOW(), NOW(), NULL),
    (2, 5, 'OrderNo2', 5,  5, 500,  'StatusCode5', 'TID5', NOW(), NOW(), NOW(), NOW(), NULL),
    (2, 6, 'OrderNo2', 6,  6, 600,  'StatusCode6', 'TID6', NOW(), NOW(), NOW(), NOW(), NULL),
    (3, 7, 'OrderNo3', 7,  7, 700,  'StatusCode7', 'TID7', NOW(), NOW(), NOW(), NOW(), NULL),
    (3, 8, 'OrderNo3', 8,  8, 800,  'StatusCode8', 'TID8', NOW(), NOW(), NOW(), NOW(), NULL),
    (3, 9, 'OrderNo3', 9,  9, 900,  'StatusCode9', 'TID9', NOW(), NOW(), NOW(), NOW(), NULL),
    (4, 10, 'OrderNo4', 10,  10, 1000,  'StatusCode10', 'TID10', NOW(), NOW(), NOW(), NOW(), NULL),
    (4, 10, 'OrderNo4', 10,  10, 1000,  'StatusCode10', 'TID10', NOW(), NOW(), NOW(), NOW(), NULL),
    (4, 10, 'OrderNo4', 10,  10, 1000,  'StatusCode10', 'TID10', NOW(), NOW(), NOW(), NOW(), NULL),
    (5, 10, 'OrderNo5', 10,10, 1000,  'StatusCode10', 'TID10', NOW(), NOW(), NOW(), NOW(), NULL),
    (5, 10, 'OrderNo5', 10,  10, 1000,  'StatusCode10', 'TID10', NOW(), NOW(), NOW(), NOW(), NULL),
    (5, 10, 'OrderNo5', 10,  10, 1000,  'StatusCode10', 'TID10', NOW(), NOW(), NOW(), NOW(), NULL),
    (6, 10, 'OrderNo6', 10,  10, 1000,  'StatusCode10', 'TID10', NOW(), NOW(), NOW(), NOW(), NULL),
    (6, 10, 'OrderNo6', 10, 10, 1000,  'StatusCode10', 'TID10', NOW(), NOW(), NOW(), NOW(), NULL),
    (6, 10, 'OrderNo6', 10,  10, 1000,  'StatusCode10', 'TID10', NOW(), NOW(), NOW(), NOW(), NULL),
    (6, 10, 'OrderNo6', 10,  10, 1000,  'StatusCode10', 'TID10', NOW(), NOW(), NOW(), NOW(), NULL);

INSERT INTO orders (member_id, address_id, order_name, order_no, total_discount, total_price, real_price, tid, created_at, updated_at, deleted_at)
VALUES
    (11, 1, 'OrderName1', 'OrderNo1', 100, 200, 100, 'TID1', NOW(), NOW(), NULL),
    (11, 2, 'OrderName2', 'OrderNo2', 200, 300, 100, 'TID2', NOW(), NOW(), NULL),
    (11, 3, 'OrderName3', 'OrderNo3', 300, 400, 100, 'TID3', NOW(), NOW(), NULL),
    (11, 4, 'OrderName4', 'OrderNo4', 400, 500, 100, 'TID4', NOW(), NOW(), NULL),
    (11, 5, 'OrderName5', 'OrderNo5', 500, 600, 100, 'TID5', NOW(), NOW(), NULL),
    (11, 6, 'OrderName6', 'OrderNo6', 600, 700, 100, 'TID6', NOW(), NOW(), NULL);

INSERT INTO status_code (type, code, name, created_at, updated_at, deleted_at)
VALUES
    ('Type1', 'Code1', 'Name1', NOW(), NOW(), NULL),
    ('Type2', 'Code2', 'Name2', NOW(), NOW(), NULL),
    ('Type3', 'Code3', 'Name3', NOW(), NOW(), NULL),
    ('Type4', 'Code4', 'Name4', NOW(), NOW(), NULL),
    ('Type5', 'Code5', 'Name5', NOW(), NOW(), NULL),
    ('Type6', 'Code6', 'Name6', NOW(), NOW(), NULL),
    ('Type7', 'Code7', 'Name7', NOW(), NOW(), NULL),
    ('Type8', 'Code8', 'Name8', NOW(), NOW(), NULL),
    ('Type9', 'Code9', 'Name9', NOW(), NOW(), NULL),
    ('Type10', 'Code10', 'Name10', NOW(), NOW(), NULL);

INSERT INTO prod_tag (tag_id, product_id, created_at, updated_at, deleted_at)
VALUES
    (1, 1, NOW(), NOW(), NULL),
    (2, 2, NOW(), NOW(), NULL),
    (3, 3, NOW(), NOW(), NULL),
    (4, 4, NOW(), NOW(), NULL),
    (5, 5, NOW(), NOW(), NULL),
    (6, 6, NOW(), NOW(), NULL),
    (7, 7, NOW(), NOW(), NULL),
    (8, 8, NOW(), NOW(), NULL),
    (9, 9, NOW(), NOW(), NULL),
    (10, 10, NOW(), NOW(), NULL);

INSERT INTO tag (name, created_at, updated_at, deleted_at)
VALUES
    ('Tag1', NOW(), NOW(), NULL),
    ('Tag2', NOW(), NOW(), NULL),
    ('Tag3', NOW(), NOW(), NULL),
    ('Tag4', NOW(), NOW(), NULL),
    ('Tag5', NOW(), NOW(), NULL),
    ('Tag6', NOW(), NOW(), NULL),
    ('Tag7', NOW(), NOW(), NULL),
    ('Tag8', NOW(), NOW(), NULL),
    ('Tag9', NOW(), NOW(), NULL),
    ('Tag10', NOW(), NOW(), NULL);

INSERT INTO options (option_category_id, name, created_at, updated_at, deleted_at)
VALUES
    (1, 'Option1', NOW(), NOW(), NULL),
    (2, 'Option2', NOW(), NOW(), NULL),
    (3, 'Option3', NOW(), NOW(), NULL),
    (4, 'Option4', NOW(), NOW(), NULL),
    (5, 'Option5', NOW(), NOW(), NULL),
    (6, 'Option6', NOW(), NOW(), NULL),
    (7, 'Option7', NOW(), NOW(), NULL),
    (8, 'Option8', NOW(), NOW(), NULL),
    (9, 'Option9', NOW(), NOW(), NULL),
    (10, 'Option10', NOW(), NOW(), NULL);

INSERT INTO option_category (name, created_at, updated_at, deleted_at)
VALUES
    ('OptionCategory1', NOW(), NOW(), NULL),
    ('OptionCategory2', NOW(), NOW(), NULL),
    ('OptionCategory3', NOW(), NOW(), NULL),
    ('OptionCategory4', NOW(), NOW(), NULL),
    ('OptionCategory5', NOW(), NOW(), NULL),
    ('OptionCategory6', NOW(), NOW(), NULL),
    ('OptionCategory7', NOW(), NOW(), NULL),
    ('OptionCategory8', NOW(), NOW(), NULL),
    ('OptionCategory9', NOW(), NOW(), NULL),
    ('OptionCategory10', NOW(), NOW(), NULL);

INSERT INTO image (type, url, created_at, updated_at, deleted_at)
VALUES
    ('Type1', 'URL1', NOW(), NOW(), NULL),
    ('Type2', 'URL2', NOW(), NOW(), NULL),
    ('Type3', 'URL3', NOW(), NOW(), NULL),
    ('Type4', 'URL4', NOW(), NOW(), NULL),
    ('Type5', 'URL5', NOW(), NOW(), NULL),
    ('Type6', 'URL6', NOW(), NOW(), NULL),
    ('Type7', 'URL7', NOW(), NOW(), NULL),
    ('Type8', 'URL8', NOW(), NOW(), NULL),
    ('Type9', 'URL9', NOW(), NOW(), NULL),
    ('Type10', 'URL10', NOW(), NOW(), NULL);

INSERT INTO product_image (product_id, image_id, seq_no, created_at, updated_at, deleted_at)
VALUES
    (1, 1, 1, NOW(), NOW(), NULL),
    (2, 2, 1, NOW(), NOW(), NULL),
    (3, 3, 1, NOW(), NOW(), NULL),
    (4, 4, 1, NOW(), NOW(), NULL),
    (5, 5, 1, NOW(), NOW(), NULL),
    (6, 6, 1, NOW(), NOW(), NULL),
    (7, 7, 1, NOW(), NOW(), NULL),
    (8, 8, 1, NOW(), NOW(), NULL),
    (9, 9, 1, NOW(), NOW(), NULL),
    (10, 10, 1, NOW(), NOW(), NULL);

INSERT INTO category (parent_id, level, name, created_at, updated_at, deleted_at)
VALUES
    (NULL, 1, 'CategoryName1', NOW(), NOW(), NULL),
    (NULL, 1, 'CategoryName2', NOW(), NOW(), NULL),
    (NULL, 1, 'CategoryName3', NOW(), NOW(), NULL),
    (NULL, 1, 'CategoryName4', NOW(), NOW(), NULL),
    (NULL, 1, 'CategoryName5', NOW(), NOW(), NULL),
    (NULL, 1, 'CategoryName6', NOW(), NOW(), NULL),
    (NULL, 1, 'CategoryName7', NOW(), NOW(), NULL),
    (NULL, 1, 'CategoryName8', NOW(), NOW(), NULL),
    (NULL, 1, 'CategoryName9', NOW(), NOW(), NULL),
    (NULL, 1, 'CategoryName10', NOW(), NOW(), NULL);

INSERT INTO address (member_id, alias, name, phone_no, city, street, zipcode, detail, is_default, created_at, updated_at, deleted_at)
VALUES
    (1, 'Alias1', 'Name1', '123456789', 'City1', 'Street1', 'Zipcode1', 'Detail1', 1, NOW(), NOW(), NULL),
    (2, 'Alias2', 'Name2', '123456789', 'City2', 'Street2', 'Zipcode2', 'Detail2', 1, NOW(), NOW(), NULL),
    (3, 'Alias3', 'Name3', '123456789', 'City3', 'Street3', 'Zipcode3', 'Detail3', 1, NOW(), NOW(), NULL),
    (4, 'Alias4', 'Name4', '123456789', 'City4', 'Street4', 'Zipcode4', 'Detail4', 1, NOW(), NOW(), NULL),
    (5, 'Alias5', 'Name5', '123456789', 'City5', 'Street5', 'Zipcode5', 'Detail5', 1, NOW(), NOW(), NULL),
    (6, 'Alias6', 'Name6', '123456789', 'City6', 'Street6', 'Zipcode6', 'Detail6', 1, NOW(), NOW(), NULL),
    (7, 'Alias7', 'Name7', '123456789', 'City7', 'Street7', 'Zipcode7', 'Detail7', 1, NOW(), NOW(), NULL),
    (8, 'Alias8', 'Name8', '123456789', 'City8', 'Street8', 'Zipcode8', 'Detail8', 1, NOW(), NOW(), NULL),
    (9, 'Alias9', 'Name9', '123456789', 'City9', 'Street9', 'Zipcode9', 'Detail9', 1, NOW(), NOW(), NULL),
    (10, 'Alias10', 'Name10', '123456789', 'City10', 'Street10', 'Zipcode10', 'Detail10', 1, NOW(), NOW(), NULL);

INSERT INTO membership (member_id, cid, tid, sid, item_name, subs_order_no,  status_code, payment_method, quantity, total_amount, discount_amount, tax_free_amount, card_issuer_name, card_purchase_name, card_no, card_install_month, created_at, updated_at, deleted_at)
VALUES
    (1, 'CID1', 'TID1', 'SID1', 'ItemName1', 'SubsOrderNo1',  'StatusCode1', 'PaymentMethod1', 1, 1000, 100, 50, 'CardIssuerName1', 'CardPurchaseName1', 'CardNo1', 12, NOW(), NOW(), NULL),
    (2, 'CID2', 'TID2', 'SID2', 'ItemName2', 'SubsOrderNo2',  'StatusCode2', 'PaymentMethod2', 2, 2000, 200, 100, 'CardIssuerName2', 'CardPurchaseName2', 'CardNo2', 6, NOW(), NOW(), NULL),
    (3, 'CID3', 'TID3', 'SID3', 'ItemName3', 'SubsOrderNo3',  'StatusCode3', 'PaymentMethod3', 3, 3000, 300, 150, 'CardIssuerName3', 'CardPurchaseName3', 'CardNo3', 3, NOW(), NOW(), NULL),
    (4, 'CID4', 'TID4', 'SID4', 'ItemName4', 'SubsOrderNo4',  'StatusCode4', 'PaymentMethod4', 4, 4000, 400, 200, 'CardIssuerName4', 'CardPurchaseName4', 'CardNo4', 9, NOW(), NOW(), NULL),
    (5, 'CID5', 'TID5', 'SID5', 'ItemName5', 'SubsOrderNo5',  'StatusCode5', 'PaymentMethod5', 5, 5000, 500, 250, 'CardIssuerName5', 'CardPurchaseName5', 'CardNo5', 6, NOW(), NOW(), NULL),
    (6, 'CID6', 'TID6', 'SID6', 'ItemName6', 'SubsOrderNo6',  'StatusCode6', 'PaymentMethod6', 6, 6000, 600, 300, 'CardIssuerName6', 'CardPurchaseName6', 'CardNo6', 12, NOW(), NOW(), NULL),
    (7, 'CID7', 'TID7', 'SID7', 'ItemName7', 'SubsOrderNo7',  'StatusCode7', 'PaymentMethod7', 7, 7000, 700, 350, 'CardIssuerName7', 'CardPurchaseName7', 'CardNo7', 3, NOW(), NOW(), NULL),
    (8, 'CID8', 'TID8', 'SID8', 'ItemName8', 'SubsOrderNo8',  'StatusCode8', 'PaymentMethod8', 8, 8000, 800, 400, 'CardIssuerName8', 'CardPurchaseName8', 'CardNo8', 6, NOW(), NOW(), NULL),
    (9, 'CID9', 'TID9', 'SID9', 'ItemName9', 'SubsOrderNo9',  'StatusCode9', 'PaymentMethod9', 9, 9000, 900, 450, 'CardIssuerName9', 'CardPurchaseName9', 'CardNo9', 9, NOW(), NOW(), NULL),
    (10, 'CID10', 'TID10', 'SID10', 'ItemName10', 'SubsOrderNo10',  'StatusCode10', 'PaymentMethod10', 10, 10000, 1000, 500, 'CardIssuerName10', 'CardPurchaseName10', 'CardNo10', 12, NOW(), NOW(), NULL);

INSERT INTO member ( membership, email, password, name, phone_no, is_alert, is_agree, created_at, updated_at, deleted_at)
VALUES
    ( 'Membership1', 'email1@example.com', 'password1', 'MemberName1', '123456789', 1, 1, NOW(), NOW(), NULL),
    ('Membership2', 'email2@example.com', 'password2', 'MemberName2', '123456789', 1, 1, NOW(), NOW(), NULL),
    ( 'Membership3', 'email3@example.com', 'password3', 'MemberName3', '123456789', 1, 1, NOW(), NOW(), NULL),
    ('Membership4', 'email4@example.com', 'password4', 'MemberName4', '123456789', 1, 1, NOW(), NOW(), NULL),
    ( 'Membership5', 'email5@example.com', 'password5', 'MemberName5', '123456789', 1, 1, NOW(), NOW(), NULL),
    ( 'Membership6', 'email6@example.com', 'password6', 'MemberName6', '123456789', 1, 1, NOW(), NOW(), NULL),
    ( 'Membership7', 'email7@example.com', 'password7', 'MemberName7', '123456789', 1, 1, NOW(), NOW(), NULL),
    ( 'Membership8', 'email8@example.com', 'password8', 'MemberName8', '123456789', 1, 1, NOW(), NOW(), NULL),
    ( 'Membership9', 'email9@example.com', 'password9', 'MemberName9', '123456789', 1, 1, NOW(), NOW(), NULL),
    ('Membership10', 'email10@example.com', 'password10', 'MemberName10', '123456789', 1, 1, NOW(), NOW(), NULL);

INSERT INTO prod_option (product_id, option_id, created_at, updated_at, deleted_at)
VALUES
    (1, 1, NOW(), NOW(), null),
    (2, 2, NOW(), NOW(), null),
    (3, 3, NOW(), NOW(), null),
    (4, 4, NOW(), NOW(), null),
    (5, 5, NOW(), NOW(), null),
    (6, 6, NOW(), NOW(), null),
    (7, 7, NOW(), NOW(), null),
    (8, 8, NOW(), NOW(), null),
    (9, 9, NOW(), NOW(), null),
    (10, 10, NOW(), NOW(), null);

INSERT INTO product (category_id, is_own, name, price, is_subs, stock, thumb_img, discount_rate, product_no, created_at, updated_at, deleted_at)
VALUES
    (1, 1, 'Product1', 100, 0, 10, 'ThumbImg1', 5, 'ProductNo1', NOW(), NOW(), null),
    (2, 1, 'Product2', 200, 0, 20, 'ThumbImg2', 10, 'ProductNo2', NOW(), NOW(), null),
    (3, 1, 'Product3', 300, 0, 30, 'ThumbImg3', 15, 'ProductNo3', NOW(), NOW(), null),
    (4, 1, 'Product4', 400, 0, 40, 'ThumbImg4', 20, 'ProductNo4', NOW(), NOW(), null),
    (5, 1, 'Product5', 500, 0, 50, 'ThumbImg5', 25, 'ProductNo5', NOW(), NOW(), null),
    (6, 1, 'Product6', 600, 0, 60, 'ThumbImg6', 30, 'ProductNo6', NOW(), NOW(), null),
    (7, 1, 'Product7', 700, 0, 70, 'ThumbImg7', 35, 'ProductNo7', NOW(), NOW(), null),
    (8, 1, 'Product8', 800, 0, 80, 'ThumbImg8', 40, 'ProductNo8', NOW(), NOW(), null),
    (9, 1, 'Product9', 900, 0, 90, 'ThumbImg9', 45, 'ProductNo9', NOW(), NOW(), null),
    (10, 1, 'Product10', 1000, 0, 100, 'ThumbImg10', 50, 'ProductNo10', NOW(), NOW(), null);
