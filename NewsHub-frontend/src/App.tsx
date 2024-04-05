import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import DefaultLayout from "./containers/default/DefaultLayout";
import HomePage from "./views/Home";
import CategoryCreatePage from "./components/category/create/CategoryCreatePage";
import CategoryEditPage from "./components/category/edit/CategoryEditPage";
import TagListPage from "./components/tag/list/TagListPage";
import TagCreatePage from "./components/tag/create/TagCreatePage"; // Додайте імпорт для сторінки створення тегу
import TagEditPage from "./components/tag/edit/TagEditPage"; // Додайте імпорт для сторінки редагування тегу
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
          <Route path="/" element={<DefaultLayout />}>
            <Route index element={<HomePage />} />
            <Route path="category/create" element={<CategoryCreatePage />}/>
            <Route path="category/edit/:id" element={<CategoryEditPage />} />
            <Route path="tag" element={<TagListPage />}/>
            <Route path="tag/create" element={<TagCreatePage />}/> 
            <Route path="tag/edit/:id" element={<TagEditPage />} /> 
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
