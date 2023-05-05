import logo from '../../../assets/images/intouch-logo2.png'
import './Footer.css'

const Footer = () => {
    return (
        <div className='footer-section'>
            <div className='logo-section'>
                <img src={logo} className='logo-img ' />
            </div>
            <div className='company-info-section'>
                <p className='description'>
                    Intouch Communications Ltd is a Technology and Consulting services provider,<br/> providing Cloud Communication Platforms that cater to enterprises,<br/> Government and NGOs, other technology service providers, and individuals.<br /><br /> Our range of enterprise services and solutions includes smart solutions in <br />-Messaging, <br />-USSD, <br />-Payments, and Software + Mobility Solutions.

                    <br /><br />
                    2nd Floor, Irembo House
                    Remera, Kigali
                    Rwanda.
                    +250788304441 +250780880209
                </p>
            </div>
            <div className='quick-link-section'>
                <div className='quick-link-title description'>
                    Quick Links
                </div><br />
                <div className='quick-links'>
                    <p className='description'>
                    Overview<br/>
                    A2P HTTP API<br/>
                    SMPP Access<br/>
                    Web Portal<br/>
                    Business Accounts<br/>
                    Pricing & Packages<br/>
                    </p>
                </div>


            </div>

            {/* company links section */}
            <div className='company-link'>
                <div className='company-links-title description'>
                    Company
                </div><br/>

                <div className='company-links'>
                    <p className='description'>
                    About Us<br/>
                    SMS Gateway<br/>
                    Payment Gateway<br/>
                    USSD Gateway<br/>
                    Mobility Solutions<br/>
                    Terms of Use<br/>
                    </p>
                </div>
            </div>


        </div>
    )
}

export default Footer