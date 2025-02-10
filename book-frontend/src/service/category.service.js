import axios from "axios";
import { BASE_API_URL } from "../common/constant";

const API_URL = BASE_API_URL + "/api/categories";

class CategoryService {

    saveCategory(category) {
        return axios.post(API_URL + "/", category);
    }

    deleteCategory(category) {

        return axios.delete(API_URL + "/" + category.id);
    }

    getAllCategory() {
        return axios.get(API_URL + "/");
    }

    updateCategory(category) {
        //console.log(category);
        return axios.put(API_URL + "/" + category.id, category);
    }

}

export default new CategoryService();