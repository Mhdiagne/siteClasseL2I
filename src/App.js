
import './Styles/bootstrap.min.css'

import Accueil from './Pages/Accueil';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Cours from './Pages/Cours';
import Forum from "./Pages/Forum";
import Maquette from "./Pages/Maquette";
import Apropos from "./Pages/Apropos";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
      <Routes>
        <Route path='/' element={<Accueil/>}/>
        <Route path='/cours' element={<Cours/>}/>
          <Route path='/forum' element={<Forum/>}/>
          <Route path='/maquette' element={<Maquette/>} />
          <Route path='/a-propos' element={<Apropos/>} />
        
      </Routes>
     </BrowserRouter>
    </div>
  );
}

export default App;
