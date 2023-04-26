import { useState } from 'react'
import { NavLink } from 'react-router-dom'
import './Navbar.css'
import logo from '../../../assets/icons/intouch-logo.png'

import {BsFillPersonFill} from 'react-icons/bs'
const Navbar = () => {

    const [showNavbar,setShowNavbar] = useState(false)

    const handleShowNavbar = () =>{
        setShowNavbar(!showNavbar)
    }
    return (
        <nav className='navbar'>
            {/* <div className='container'> */}
                <div className='logo'>
                    <img src={logo} className='logo-img m-0 h-100'/>
                </div>
                <div className='menu-icon' onClick={handleShowNavbar}>
                  --
                  --
                  --
                </div>
                <div className={`nav-elements  ${showNavbar && 'active'}`}>
                    <ul>
                        <li>
                            <NavLink to="/">Home</NavLink>
                        </li>
                        <li>
                            <NavLink to="/overview">Overview</NavLink>
                        </li>
                        <li>
                            <NavLink to="/httpapi">Http API</NavLink>
                        </li>
                        <li>
                            <NavLink to="/smppaccess">SMPP Access</NavLink>
                        </li>
                        <li>
                            <NavLink to="/pricing">Pricing</NavLink>
                        </li>
                        <li>
                            <NavLink to="/login" className="login-button">Log In</NavLink>
                        </li>
                        <li>
                            <NavLink to="/createaccount" className="signup-button">Create Account</NavLink>
                        </li>


                    </ul>
                </div>
            {/* </div> */}
        </nav>
    )
}

export default Navbar
