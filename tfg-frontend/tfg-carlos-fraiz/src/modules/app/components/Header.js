import React from 'react';
import {useSelector} from 'react-redux';
import {Link} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';

import users from '../../users';

const Header = () => {

    const userName = useSelector(users.selectors.getUserName);
    
    return (

        <nav className="navbar navbar-expand-lg navbar-light bg-light border">
            <Link className="navbar-brand" to="/">
                <FormattedMessage id="project.app.Header.title"/>
            </Link>
            <button className="navbar-toggler" type="button" 
                data-toggle="collapse" data-target="#navbarSupportedContent" 
                aria-controls="navbarSupportedContent" aria-expanded="false" 
                aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>

            <div className="collapse navbar-collapse d-flex justify-content-end" id="navbarSupportedContent">

                
                {userName ? 

                <ul className="navbar-nav">

                    <li className="nav-item btn btn btn-info my-0 mr-3 py-0">
                        <Link className="nav-link" to="/clasificacion/upload">
                            <FormattedMessage id="project.clasificacion.header.upload"/>&nbsp;&nbsp;
                            <i className="fa fa-upload" aria-hidden="true"></i>
                        </Link>
                    </li>
                    
                    <li className="nav-item btn btn-outline-secondary my-0 mr-3 py-0">
                        <Link className="nav-link" to="/conjuntos">
                            <FormattedMessage id="project.conjunto.button.conjuntos"/>&nbsp;&nbsp;
                        </Link>
                    </li>

                    <li className="nav-item dropdown">

                        <a className="dropdown-toggle nav-link" href="/"
                            data-toggle="dropdown">
                            <span className="fas fa-user"></span>&nbsp;
                            {userName}
                        </a>
                        <div className="dropdown-menu dropdown-menu-right">
                            <Link className="dropdown-item" to="/users/update-profile">
                                <FormattedMessage id="project.users.UpdateProfile.title"/>
                            </Link>
                            <Link className="dropdown-item" to="/users/change-password">
                                <FormattedMessage id="project.users.ChangePassword.title"/>
                            </Link>
                            <div className="dropdown-divider"></div>
                            <Link className="dropdown-item" to="/users/logout">
                                <FormattedMessage id="project.app.Header.logout"/>
                            </Link>
                        </div>

                    </li>

                  
                </ul>
                
                :

                <ul className="navbar-nav">
                    <li className="nav-item">
                        <Link className="nav-link" to="/users/login">
                            <FormattedMessage id="project.users.Login.title"/>
                        </Link>
                    </li>
                </ul>
                
                }

            </div>
        </nav>

    );

};

export default Header;
