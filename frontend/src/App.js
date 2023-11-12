import { BrowserRouter as Router, Routes, Route, BrowserRouter } from "react-router-dom";
import './App.css';
import Home from './pages/home/Home';
import Navbar from './components/navbar';
import AddWordList from "./pages/wordList/AddWordList";
import EditWordList from "./pages/wordList/EditWordList";
import ViewWordList from "./pages/wordList/ViewWordList";
import Diary from "./pages/diary/Diary";
import Words from "./pages/word/Words";
import ViewDiary from "./pages/diary/ViewDiary";
import EditDiary from "./pages/diary/EditDiary";
import AddDiary from "./pages/diary/AddDiary";
import ViewWord from "./pages/word/ViewWord";
import EditWord from "./pages/word/EditWord";
import AddWord from "./pages/word/AddWord";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
          <Navbar />
          <Routes>
            <Route exact path="/" element={<Home />} />
            <Route exact path="/addwordlist" element={<AddWordList />} />
            <Route exact path="/editWordList/:id" element={<EditWordList />} />
            <Route exact path="/viewWordList/:id" element={<ViewWordList />} />
            <Route exact path="/viewdiarys" element={<Diary/>} />
            <Route exact path="/viewdiarydetails/:id" element={<ViewDiary/>} />
            <Route exact path="/editdiary/:id" element={<EditDiary/>} />
            <Route exact path="/adddiary" element={<AddDiary/>} />
            <Route exact path="/viewwords" element={<Words/>} />
            <Route exact path="/viewworddetails/:id" element={<ViewWord/>} />
            <Route exact path="/editword/:id" element={<EditWord/>} />
            <Route exact path="/addword/:id" element={<AddWord/>} />
          </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
