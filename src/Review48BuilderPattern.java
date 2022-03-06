public class Review48BuilderPattern {

    public static void main(String[] args) {
        Product product = new Product.ProductBuilder().setName("iMac").setPrice(1299).setWeight(20).build();
        System.out.println(product.toString());
    }
}

class Product {
    private String name;
    private int weight;
    private int price;

    private Product(ProductBuilder builder) {
        this.name = builder.name;
        this.weight = builder.weight;
        this.price = builder.price;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product name: " + name + ", weight: " + weight + ", price: " + price;
    }

    public static class ProductBuilder {
        private String name;
        private int weight;
        private int price;

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public ProductBuilder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}




