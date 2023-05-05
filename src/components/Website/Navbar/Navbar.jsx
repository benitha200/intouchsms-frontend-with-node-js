import { useState } from 'react'
import { NavLink } from 'react-router-dom'
import './Navbar.css'
import logo from '../../../assets/icons/intouch-logo.png'
import { AiOutlineMenu } from 'react-icons/ai';
import { BsMap, BsPhoneVibrate, BsTelephoneForward, BsTelephoneForwardFill } from 'react-icons/bs';
import { Button } from 'react-bootstrap';
import HeadShake from 'react-reveal/HeadShake';

// import {BsFillPersonFill} from 'react-icons/bs'
const Navbar = () => {

    const [showNavbar,setShowNavbar] = useState(false)

    const handleShowNavbar = () =>{
        setShowNavbar(!showNavbar)
    }
    return (
        <>
        <div className='head'>
        <div className=' head1 d-flex flex-row justify-content-end pl-2 gap-5'>
            <span className='description'><BsMap/> Kigali, Rwanda</span>
            <span className='description'><BsTelephoneForward/>+250788304441 , +250780880209</span>
            <HeadShake><span className="head-button description p-1"><BsTelephoneForwardFill/> Stay Intouch </span> </HeadShake>
        </div>
        <nav className='navbar'>
            {/* <div className='container'> */}
                <div className='logo'>
                    <img src={logo} className='logo-image m-0 h-100'/>

                </div>
                <div className='menu-icon' onClick={handleShowNavbar}>
                <AiOutlineMenu/>
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
                            <NavLink to="/smsreseller">SMS Reseller</NavLink>
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
        </div>
        </>
    )
}

export default Navbar
