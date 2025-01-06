import { useState } from "react";
import { Button } from "./ui/button";
import { DialogDescription, DialogHeader, DialogTitle } from "./ui/dialog";
import { ScrollArea } from "./ui/scroll-area";
import useFetchCategories from "@/hooks/useFetchCategories";

interface CategoriesFormProps {
  setCurrentForm: (form: "transactionForm" | "categoriesForm") => void;
  token: string | null;
  initialCategories: any[];
  iconMap: {
    [key: string]: React.ReactNode;
  }
}

const CategoriesForm: React.FC<CategoriesFormProps> = ({setCurrentForm, token, initialCategories, iconMap}) => {

  const [categories, setCategories] = useState<Category[]>(initialCategories);
  const [selectedCategory, setSelectedCategory] = useState<Category | null>(null);
  const [newCategoryName, setNewCategoryName] = useState('');
  const [selectedIcon, setSelectedIcon] = useState('');
  
  const { editCategory, removeCategory } = useFetchCategories(token);

  type Category = {
    categoryId: number | string;
    name: string;
    icon: string;
  };
  

  const handleEdit = (category: Category) => {
    setSelectedCategory(category);
    setNewCategoryName(category.name);
    setSelectedIcon(category.icon);
  };

  const handleSave = async () => {
    try {
      if (selectedCategory) {
        const updatedCategory = await editCategory(selectedCategory.categoryId, 
          {name: newCategoryName, icon: selectedIcon});

        setCategories((prev) =>
          prev.map((cat) =>
            cat.categoryId === selectedCategory.categoryId ? updatedCategory : cat
          )
        );
      } else {
        const newCategory: Category = {
          categoryId: Date.now(), 
          name: newCategoryName,
          icon: selectedIcon,
        };
        setCategories((prev) => [...prev, newCategory]);
      }

      setSelectedCategory(null);
      setNewCategoryName('');
      setSelectedIcon('');
    } catch (err) {
      console.error("Error saving category:", err);
      alert("Failed to save category.");
    }
  };

  const handleDelete = async (categoryId: number | string) => {
    try {
      await removeCategory(categoryId); 
      setCategories((prev) => prev.filter((cat) => cat.categoryId !== categoryId));
    } catch (err) {
      console.error("Error deleting category:", err);
      alert("Failed to delete category.");
    }
  };

 return (
    <>
      <DialogHeader>
        <DialogTitle>Edit Categories</DialogTitle>
        <DialogDescription>
          Manage categories for your transaction.
        </DialogDescription>
      </DialogHeader>
      <DialogTitle>Your Categories</DialogTitle>
      
      <ScrollArea className="h-40 w-70 rounded-md border">
      <div className="flex gap-2 flex-wrap">
      {categories.map((category) => (
            <div
            key={category.categoryId}
            className="flex items-center justify-between p-2 border rounded-md"
          >
            <div className="flex items-center gap-2">
              <span>{category.name}</span>
              <span>{iconMap[category.icon]}</span>
            </div>
            <div className="flex gap-2">
              <button
                className="p-1 text-blue-500 border rounded-md"
                onClick={() => handleEdit(category)}
              >
                Edit
              </button>
              <button
                className="p-1 text-red-500 border rounded-md"
                onClick={() => handleDelete(category.categoryId)}
              >
                Delete
              </button>
            </div>
            </div>
          ))}
      </div>
      </ScrollArea>

      <form className="mt-4 flex flex-col gap-2">
        <input
          type="text"
          placeholder="Category Name"
          value={newCategoryName}
          onChange={(e) => setNewCategoryName(e.target.value)}
          className="border rounded-md p-2"
        />
        <div className="flex gap-2">
          {Object.keys(iconMap).map((iconKey) => (
            <button
              key={iconKey}
              type="button"
              onClick={() => setSelectedIcon(iconKey)}
              className={`p-2 border rounded-md ${
                selectedIcon === iconKey ? 'bg-blue-500 text-white' : ''
              }`}
            >
              {iconMap[iconKey]}
            </button>
          ))}
        </div>
        <button
          type="button"
          onClick={handleSave}
          className="p-2 bg-green-500 text-white rounded-md"
        >
          {selectedCategory ? 'Update Category' : 'Add Category'}
        </button>
      </form>

      
      <div>
        <Button onClick={() => setCurrentForm("transactionForm")}>
          Back to Transaction
        </Button>
      </div>
    </>
 )
}

export default CategoriesForm;