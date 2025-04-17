local image = love.graphics.newImage('love4me.png')

function love.load()
  
end

function love.draw()
  local w, h = love.system.getDimensions()
  love.graphics.draw(image, w/2, h/2)
end
