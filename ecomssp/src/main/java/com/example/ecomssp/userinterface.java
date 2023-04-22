package com.example.ecomssp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class userinterface {

    GridPane loginpage;
    HBox headerbar;
    HBox footerBar;
    Button signInButton;

    Label welcomelabel;

    VBox body;

    Customer loggedInCustomer;

    ProductList productList=new ProductList();
    VBox productPage;

    Button placeOrderButton = new Button("Place Order");

    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();
    public BorderPane createcontent() {
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);
        //root.getChildren().add(loginpage); //method to add nodes as children to pane
       //  root.setCenter(loginpage);
        root.setTop(headerbar);
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        productPage = productList.getAllProducts();
        body.getChildren().add(productPage);
        root.setBottom(footerBar);

        return root;
    }
    public userinterface(){
        createloginpage();
        createheaderbar();
        createFooterBar();
    }
    private void createloginpage(){
        Text userNameText=new Text("USERNAME");
        Text passwordText=new Text("PASSWORD");

        TextField userName=new TextField("divs@gmail.com");
        userName.setPromptText("Type your user name here");
        PasswordField password= new PasswordField();
        password.setText("efg");
        password.setPromptText("Type your password here");

        Label messagelabel=new Label("Hello");
        Button loginbutton=new Button("Login");

        loginpage=new GridPane();
        //loginpage.setStyle(" -fx-background-color: grey;");
        loginpage.setAlignment(Pos.CENTER);
        loginpage.setHgap(10);
        loginpage.setVgap(10);
        loginpage.add(userNameText, 0, 0);
        loginpage.add(userName, 1, 0);
        loginpage.add(passwordText, 0, 1);
        loginpage.add(password, 1, 1);
        loginpage.add(messagelabel, 0, 2);
        loginpage.add(loginbutton, 1, 2);

        loginbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name=userName.getText();
                String pass=password.getText();
                Login login = new Login();
               loggedInCustomer = login.customerLogin(name, pass);
                if(loggedInCustomer != null) {
                    messagelabel.setText("Welcome! " + loggedInCustomer.getName());
                    welcomelabel.setText("Welcome! "+loggedInCustomer.getName());
                    headerbar.getChildren().add(welcomelabel);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                } else
                    messagelabel.setText("Login Failed !! please try again");
            }
        });
    }


    private void createheaderbar(){
        Button homeButton = new Button();
        Image img = new Image("C:\\Users\\SAURABH\\IdeaProjects\\ecomssp\\src\\png-transparent-e-commerce-logo-logo-e-commerce-electronic-business-ecommerce-angle-text-service.png");
        ImageView imageView = new ImageView();
        imageView.setImage(img);
        imageView.setFitHeight(30);
        imageView.setFitWidth(40);
        homeButton.setGraphic(imageView);

        TextField searchbar=new TextField();
        searchbar.setPromptText("Search here");
        searchbar.setPrefWidth(200);

        Button searchbutton=new Button("Search");

        signInButton = new Button("Sign In");
        welcomelabel = new Label();

        Button cartButton = new Button("View Cart");

        Button orderButton = new Button("Orders");
        headerbar=new HBox();
        headerbar.setPadding(new Insets(10));
        headerbar.setSpacing(10);
       // headerbar
        headerbar.setAlignment(Pos.CENTER);
        headerbar.getChildren().addAll(homeButton, searchbutton, signInButton, cartButton, orderButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear(); //remove everything
                body.getChildren().add(loginpage); // put login page
                headerbar.getChildren().remove(signInButton);
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getProductsInCart(itemsInCart);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false);

            }
        });
        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               // Product product = productList.getSelectedProduct();
                if(itemsInCart == null){
                    // please select a product first to place order
                    showDialog("Please add some products in the cart first!");
                    return;
                }
                if(loggedInCustomer == null){
                    showDialog("Please login first to place order!");
                    return;
                }
                int count = Order.placeMultipleOrders(loggedInCustomer, itemsInCart);
                if(count != 0){
                    showDialog("Order for "+count+" products placed successfully!");
                } else{
                    showDialog("Order failed!!");
                }
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                if(loggedInCustomer == null && headerbar.getChildren().indexOf(signInButton)==-1){
                    headerbar.getChildren().add(signInButton);
                }
            }
        });

    }



    private void createFooterBar(){

        Button buyNowButton=new Button("Buy Now!");
        Button addToCartButton = new Button("Add to Cart");

        footerBar=new HBox();
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        // headerbar
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton, addToCartButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    // please select a product first to place order
                    showDialog("Please select a product first to place order!");
                    return;
                }
                if(loggedInCustomer == null){
                    showDialog("Please login first to place order!");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer, product);
                if(status == true){
                    showDialog("Order placed successfully!");
                } else{
                    showDialog("Order failed!!");
                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    // please select a product first to place order
                    showDialog("Please select a product first to add it to the cart!");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Items added to cart");
            }
        });
    }

    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }


}
