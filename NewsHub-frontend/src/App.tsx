import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import DefaultLayout from "./containers/default/DefaultLayout";
import HomePage from "./views/Home";
import CategoryCreatePage from "./components/category/create/CategoryCreatePage";
import CategoryEditPage from "./components/category/edit/CategoryEditPage";
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
          <Route path="/" element={<DefaultLayout />}>
            <Route index element={<HomePage />} />
            <Route path="category/create" element={<CategoryCreatePage />}/> 
            <Route path="category/edit/:id" element={<CategoryEditPage />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
