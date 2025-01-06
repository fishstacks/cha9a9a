import { useState, useEffect, useContext } from "react";
import { deleteCategory, fetchCategories, updateCategory } from "../services/categoryService";

const useFetchCategories = (token: string | null) => {
  

  const [data, setData] = useState<any[]>([]); 
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const getCategories = async () => {
      setLoading(true); 
      setError(null); 
      try {
        const categories = await fetchCategories(token);
        setData(categories);
      } catch (err) {
        console.error("Error fetching categories:", err);
        setError("Failed to load categories");
      } finally {
        setLoading(false); 
      }
    };

    getCategories();
  }, [token]); 

  const editCategory = async (categoryId: string | number, updates: any) => {
    try {
      const updatedCategory = await updateCategory(token, categoryId, updates);
      setData((prev) =>
        prev.map((cat) =>
          cat.categoryId === categoryId ? { ...cat, ...updates } : cat
        )
      );
      return updatedCategory;
    } catch (err) {
      console.error("Error updating category:", err);
      throw new Error("Failed to update category");
    }
  };

  const removeCategory = async (categoryId: string | number) => {
    try {
      await deleteCategory(token, categoryId);
      setData((prev) => prev.filter((cat) => cat.categoryId !== categoryId));
    } catch (err) {
      console.error("Error deleting category:", err);
      throw new Error("Failed to delete category");
    }
  };

  return { data, loading, error, editCategory, removeCategory };
};

export default useFetchCategories;
