import React from 'react'
import './HeroSection.css'
import heroImg from '../../../../assets/images/3.jpg'
import messaging from '../../../../assets/images/messaging5.jpg'

const HeroSection = () => {
  return (
    <div>
      {/* <div className='hero-img-section'>
             <img className='hero-img' src={heroImg}/>
        </div> */}

      <div className='hero-content'>

        <div className='content-left'>

          <p className='content-header'>SMS Gateway</p>
          <span>Your Customer Engagement Platform</span>

          <button className='hero-button animated bounce'>Sign Up and Start Messaging</button>

          <span>
            With simple API intergration and cost-effective solutions,<br/> we connect you to your customers
          </span>
        </div>
        <div className='content-right'>
          {/* <img classname='content-img' width='200' height='150' src={messaging}/> */}
          <img className='content-right-img fade-in' src={messaging} />
          <div className='content-right-text fade-in'> 
            <span>Stay Connected</span>
          </div>
        </div>

      </div>
    </div>
  )
}

export default HeroSection