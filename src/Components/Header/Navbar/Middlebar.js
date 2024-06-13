import React from 'react';
import logo from '../../../Assets/img/Logo-L2i.png'; 
import '../../../Styles/Navbar-Topbar.css'; 
import { Link } from 'react-router-dom';

const Middlebar = () => {
  return (
    <div className="middlebar-container">
      <Link to='/'>
      <img src={logo} alt="Logo" className="middlebar-logo" width={140} height={150} />
      </Link>
      <div className="middlebar-text">
        <p className="text1">Licence Ingénierie</p>
        <p className="text2">Informatique : L2i</p>
      </div>
    </div>
  );
};

export default Middlebar;
