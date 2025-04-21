local image

function love.load()
  image = love.graphics.newImage('love4me.png')
end

function love.draw()
  local w, h = love.system.getDimensions()
  love.graphics.draw(image, w/2, h/2)
end
