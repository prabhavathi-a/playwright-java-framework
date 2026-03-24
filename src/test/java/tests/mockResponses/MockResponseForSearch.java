package tests.mockResponses;

public class MockResponseForSearch {
    public static final String MOCK_RESPONSE_FOR_SINGLE_PRODUCT = """
            {
                "current_page": 1,
                "data": [
                    {
                        "id": "01KM7XD15GMJRWKVPZ5KT4887B",
                        "name": "Combination Pliers",
                        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris viverra felis nec pellentesque feugiat. Donec faucibus arcu maximus, convallis nisl eu, placerat dolor. Morbi finibus neque nec tincidunt pharetra. Sed eget tortor malesuada, mollis enim id, condimentum nisi. In viverra quam at bibendum ultricies. Aliquam quis eros ex. Etiam at pretium massa, ut pharetra tortor. Sed vel metus sem. Suspendisse ac molestie turpis. Duis luctus justo massa, faucibus ornare eros elementum et. Vestibulum quis nisl vitae ante dapibus tempor auctor ut leo. Mauris consectetur et magna at ultricies. Proin a aliquet turpis.",
                        "price": 14.15,
                        "is_location_offer": false,
                        "is_rental": false,
                        "co2_rating": "D",
                        "in_stock": true,
                        "is_eco_friendly": false,
                        "product_image": {
                            "id": "01KM7XD14WJP9FT3M659PDC0VA",
                            "by_name": "Helinton Fantin",
                            "by_url": "https:\\/\\/unsplash.com\\/@fantin",
                            "source_name": "Unsplash",
                            "source_url": "https:\\/\\/unsplash.com\\/photos\\/W8BNwvOvW4M",
                            "file_name": "pliers01.avif",
                            "title": "Simple pliers"
                        },
                        "category": {
                            "id": "01KM7XD14H2S64FYNBW2PM7ZVE",
                            "name": "Pliers"
                        },
                        "brand": {
                            "id": "01KM7XD0STZ17JQNRFE9S49KWV",
                            "name": "ForgeFlex Tools"
                        }
                    }
                ],
                "from": 1,
                "last_page": 1,
                "per_page": 9,
                "to": 1,
                "total": 1
            }
            """;

    public static final String MOCK_RESPONSE_FOR_NO_PRODUCT = """
            {
                "current_page": 1,
                "data": [],
                "from": null,
                "last_page": 1,
                "per_page": 9,
                "to": null,
                "total": 0
            }
            """;
}
