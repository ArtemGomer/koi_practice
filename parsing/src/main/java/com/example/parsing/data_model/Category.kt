package data_model

data class Category(val categoryName: String, val subcategoryList: List<Subcategory> = emptyList())