import React from 'react'
import logo from '../../../assets/icons/intouch-logo.png'
import './Footer.css'

const Footer = () => {
    return (
        <div className='footer-section'>
            <div className='logo-section'>
                <img src={logo} className='logo-img' />
            </div>
            <div className='company-info-section'>
                <p>
                    Intouch Communications Ltd is a Technology and Consulting services provider,<br/> providing Cloud Communication Platforms that cater to enterprises,<br/> Government and NGOs, other technology service providers, and individuals.<br /><br /> Our range of enterprise services and solutions includes smart solutions in <br />-Messaging, <br />-USSD, <br />-Payments, and Software + Mobility Solutions.

                    <br /><br />
                    2nd Floor, Irembo House
                    Remera, Kigali
                    Rwanda.
                    +250788304441 +250780880209
                </p>
            </div>
            <div className='quick-link-section'>
                <div className='quick-link-title'>
                    Quick Links
                </div><br />
                <div className='quick-links'>
                    Overview<br/>
                    A2P HTTP API<br/>
                    SMPP Access<br/>
                    Web Portal<br/>
                    Business Accounts<br/>
                    Pricing & Packages<br/>
                </div>


            </div>

            {/* company links section */}
            <div className='company-link'>
                <div className='company-links-title'>
                    Company
                </div><br/>

                <div className='company-links'>
                    About Us<br/>
                    SMS Gateway<br/>
                    Payment Gateway<br/>
                    USSD Gateway<br/>
                    Mobility Solutions<br/>
                    Terms of Use<br/>
                </div>
            </div>


        </div>
    )
}

export default Footer