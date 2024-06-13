import React from 'react';
import '../../../Styles/Navbar-Topbar.css';
import '../../../Styles/generalCSS.css';
import {Link} from "react-router-dom";

const Navbar = () => {
    return (
        <div className="container-fluid bgCouleur2">
            <div className="container">
                <nav className="navbar text-light navbar-expand-lg py9">
                    <div className="collapse navbar-collapse " id="navbarCollapse">
                        <div className="navbar-nav ms-auto mx-xl-auto p-1">
                            <a href="/" className="nav-item nav-link active text-secondary">Accueil</a>
                            <a href="/cours" className="nav-item nav-link">Cours</a>
                            <div className="nav-item dropdown">
                                <a href="/maquette" className="nav-link dropdown-toggle" data-bs-toggle="dropdown">Maquettes</a>
                                <div className="dropdown-menu rounded">
                                    <a href="/maquette" className="dropdown-item">Licence 1</a>
                                    <a href="/maquette" className="dropdown-item">Licence 2</a>
                                    <a href="/maquette" className="dropdown-item">Licence 3</a>
                                </div>
                            </div>
                            <a href="/forum" className="nav-item nav-link">Forum</a>
                            <a href="/a-propos" className="nav-item nav-link">A propos </a>

                            <button className='mesBtn' >
                                <Link to='/' id='jajeuf'>Connexion</Link>
                            </button>
                            <button className='mesBtn' >
                                <Link to='/' id='jajeuf'>Inscription</Link>
                            </button>
                        </div>
                    </div>
                </nav>
            </div>
        </div>
    );
};

export default Navbar;
