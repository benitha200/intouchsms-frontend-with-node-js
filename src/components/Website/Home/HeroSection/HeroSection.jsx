import React from 'react'
import './HeroSection.css'
import heroImg from '../../../../assets/images/3.jpg'
import messaging from '../../../../assets/images/messaging5.jpg'

const HeroSection = () => {
  return (
    <div className='content'>
      {/* <div className='hero-img-section'>
             <img className='hero-img' src={heroImg}/>
        </div> */}

      <div className='hero-content'>

        <div className='content-left animated fadeIn'>

          <p className='content-header'>SMS Gateway</p>
          <span className='hero-text'>Your Customer Engagement Platform</span>
          
               <button className='hero-button btn animated bounce w-50 font-weight-bold'>Sign Up and Start Messaging</button>

          <span className='hero-text'>
            With simple API intergration and cost-effective solutions,<br/> we connect you to your customers
          </span>

     
          {/* <span className='hero-text'>
            With simple API intergration and cost-effective solutions,<br/> we connect you to your customers
          </span> */}
        </div>
        <div className='right-content d-flex flex-column gap-2'>
          {/* <img classname='content-img' width='200' height='150' src={messaging}/> */}
          <img className='content-right-img w-100' src={messaging} />
          <div className='content-right-text'> 
            <span>Stay Connected</span>
          </div>
        </div>

      </div>
    </div>
  )
}

export default HeroSection