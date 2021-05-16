package data_model

data class Subcategory(val subCategoryName: String, val productList: List<Product> = emptyList())